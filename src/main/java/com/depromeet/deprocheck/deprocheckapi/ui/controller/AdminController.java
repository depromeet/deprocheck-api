package com.depromeet.deprocheck.deprocheckapi.ui.controller;

import com.depromeet.deprocheck.deprocheckapi.application.assembler.AttendanceAssembler;
import com.depromeet.deprocheck.deprocheckapi.application.assembler.MemberAssembler;
import com.depromeet.deprocheck.deprocheckapi.application.assembler.SessionAssembler;
import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.ForbiddenException;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.NotFoundException;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.domain.service.AttendanceService;
import com.depromeet.deprocheck.deprocheckapi.domain.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.domain.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.domain.utils.DateTimeUtils;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "관리자 기능", description = "관리자만 사용할 수 있는 요청입니다. 인증이 필요합니다. ")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://check.depromeet.com"
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;
    private final SessionService sessionService;
    private final AttendanceService attendanceService;
    private final MemberAssembler memberAssembler;
    private final SessionAssembler sessionAssembler;
    private final AttendanceAssembler attendanceAssembler;

    /**
     * 세션 정보를 생성합니다
     */
    @ApiOperation("세션을 생성합니다.")
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                         @RequestHeader(name = "Authorization") String authorization,
                                         @ApiIgnore @RequestAttribute(name = "memberId") Integer memberId,
                                         @RequestBody SessionCreateRequest sessionCreateRequest) {
        this.checkAuthority(memberId);

        final Session session = sessionService.createSession(sessionCreateRequest);
        return sessionAssembler.toSessionResponse(session);
    }

    /**
     * 출석 정보를 조회합니다.
     */
    @ApiOperation("출석 정보를 조회합니다.")
    @GetMapping("/attendances")
    public List<SimpleAttendanceResponse> getAttendances(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                                         @RequestHeader(name = "Authorization") String authorization,
                                                         @ApiIgnore @RequestAttribute(name = "memberId") Integer memberId,
                                                         @RequestParam String date) {
        this.checkAuthority(memberId);


        return attendanceService.getAttendancesByDate(memberId, DateTimeUtils.parseDate(date))
                .stream()
                .map(attendanceAssembler::toSimpleAttendanceResponse)
                .collect(Collectors.toList());
    }

    /**
     * 회원을 추가합니다.
     */
    @ApiOperation("회원을 생성합니다. ")
    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse createMember(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                       @RequestHeader(name = "Authorization") String authorization,
                                       @ApiIgnore @RequestAttribute(name = "memberId") Integer memberId,
                                       @RequestBody MemberCreateRequest memberCreateRequest) {
        this.checkAuthority(memberId);

        final Member member = memberService.createMember(memberCreateRequest);
        return memberAssembler.toMemberResponse(member);
    }

    /**
     * 회원 정보를 조회합니다.
     */
    @ApiOperation("회원 목록을 조회합니다.")
    @GetMapping("/members")
    public List<MemberResponse> getMembers(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                           @RequestHeader(name = "Authorization") String authorization,
                                           @ApiIgnore @RequestAttribute(name = "memberId") Integer memberId,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size) {
        this.checkAuthority(memberId);

        return memberService.getMembers(PageRequest.of(page, size))
                .stream()
                .map(memberAssembler::toMemberResponse)
                .collect(Collectors.toList());
    }

    // TODO: 관리자 비밀번호 변경 기능 추가

    /**
     * 회원이 존재하고, 관리자 권한을 가졌는지 검사합니다.
     */
    private void checkAuthority(Integer memberId) {
        Assert.notNull(memberId, "'memberId' must not be null");

        try {
            final Member member = memberService.getMemberById(memberId);
            if (Authority.ADMIN != member.getAuthority()) {
                throw new ForbiddenException("관리자 권한이 없습니다. Member:" + member);
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("회원이 존재하지 않습니다. memberId:" + memberId, ex);
        }
    }
}
