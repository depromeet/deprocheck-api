package com.depromeet.deprocheck.deprocheckapi.ui.controller;

import com.depromeet.deprocheck.deprocheckapi.application.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.application.assembler.MemberAssembler;
import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.domain.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.domain.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.domain.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.domain.vo.LoginValue;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.*;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.admin.AdminAttendanceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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
    private final LoginService loginService;
    private final SessionService sessionService;
    private final JwtFactory jwtFactory;
    private final MemberAssembler memberAssembler;

    /**
     * 관리자 로그인 (이름 필드에 특정 비밀번호를 저장합니다)
     */
    @ApiOperation("멤버 이름을 입력해서 로그인합니다. 인증이 필요하지 않은 요청입니다. ")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
                               HttpServletRequest request) {
        final String adminName = (String) request.getAttribute("name");
        if (!StringUtils.isEmpty(adminName)) {
            String accessToken = jwtFactory.generateToken(adminName);
            return LoginResponse.from(accessToken);
        }
        final LoginValue loginValue = LoginValue.of(
                loginRequest.getName(),
                Authority.ADMIN
        );
        final Member member = loginService.login(loginValue);
        String accessToken = jwtFactory.generateToken(member.getName());
        return LoginResponse.from(accessToken);
    }

    /**
     * 세션 정보를 생성합니다
     */
    @ApiOperation("세션을 생성합니다.")
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                         @RequestHeader(name = "Authorization") String authorization,
                                         @RequestBody SessionCreateRequest sessionCreateRequest,
                                         HttpServletRequest request) {
        checkAuthority(request);

        final Session session = sessionService.createSession(sessionCreateRequest);
        return SessionResponse.from(session);
    }

    /**
     * 출석 정보를 조회합니다.
     */
    @ApiOperation("출석 정보를 조회합니다. (아직 구현되지 않았습니다)")
    @GetMapping("/attendances")
    public List<AdminAttendanceResponse> getAttendances(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                                        @RequestHeader(name = "Authorization") String authorization,
                                                        @RequestParam String date,
                                                        HttpServletRequest request) {
        checkAuthority(request);

        // TODO: 세션별 출석 정보 조회

        return Collections.emptyList();
    }

    /**
     * 회원을 추가합니다.
     */
    @ApiOperation("회원을 생성합니다. ")
    @PostMapping("/members")
    public MemberResponse createMember(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                       @RequestHeader(name = "Authorization") String authorization,
                                       @RequestBody MemberCreateRequest memberCreateRequest,
                                       HttpServletRequest request) {
        this.checkAuthority(request);

        Member member = memberService.createMember(memberCreateRequest);
        return memberAssembler.toMemberResponse(member);
    }

    /**
     * 회원 정보를 조회합니다.
     */
    @ApiOperation("회원 목록을 조회합니다.")
    @GetMapping("/members")
    public List<MemberResponse> getMembers(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                           @RequestHeader(name = "Authorization") String authorization,
                                           @RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size,
                                           HttpServletRequest request) {
        checkAuthority(request);

        Pageable pageable = PageRequest.of(page, size);
        return memberService.getMembers(pageable)
                .stream()
                .map(memberAssembler::toMemberResponse)
                .collect(Collectors.toList());
    }

    // TODO: 관리자 비밀번호 변경 기능 추가

    /**
     * 회원이 존재하고, 관리자 권한을 가졌는지 검사합니다.
     */
    private void checkAuthority(HttpServletRequest request) {
        Assert.notNull(request, "'request' must not be null");

        final String name = (String) request.getAttribute("name");
        if (StringUtils.isEmpty(name)) {
            throw new UnauthorizedException();
        }
        final Member member = memberService.getMemberByName(name);
        if (member == null) {
            throw new UnauthorizedException("회원이 존재하지 않습니다. 이름:" + name);
        }
        if (Authority.ADMIN != member.getAuthority()) {
            throw new UnauthorizedException("관리자 권한이 없습니다. 이름:" + name);
        }
    }
}
