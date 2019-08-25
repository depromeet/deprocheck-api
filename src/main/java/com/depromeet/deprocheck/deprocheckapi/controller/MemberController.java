package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.component.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.dto.LoginRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.LoginResponse;
import com.depromeet.deprocheck.deprocheckapi.dto.MemberAttendRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.MemberResponse;
import com.depromeet.deprocheck.deprocheckapi.dto.member.MemberAttendanceResponse;
import com.depromeet.deprocheck.deprocheckapi.exception.BadRequestException;
import com.depromeet.deprocheck.deprocheckapi.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.service.AttendanceService;
import com.depromeet.deprocheck.deprocheckapi.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.vo.AttendanceValue;
import com.depromeet.deprocheck.deprocheckapi.vo.LoginValue;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Api(value = "멤버", description = "멤버 관련 요청입니다. 인증이 필요합니다. ")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://check.depromeet.com"
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final AttendanceService attendanceService;
    private final MemberService memberService;
    private final SessionService sessionService;
    private final LoginService loginService;
    private final JwtFactory jwtFactory;

    @ApiOperation("멤버 이름을 입력해서 로그인합니다. 인증이 필요하지 않은 요청입니다.")
    @PostMapping("/members/login")
    public LoginResponse loginMember(@RequestBody LoginRequest loginRequest,
                                     HttpServletRequest request) {
        String memberName = (String) request.getAttribute("name");
        if (!StringUtils.isEmpty(memberName)) {
            String accessToken = jwtFactory.generateToken(memberName);
            return LoginResponse.from(accessToken);
        }
        LoginValue loginValue = LoginValue.of(
                loginRequest.getName(),
                Authority.MEMBER
        );
        Member member = loginService.login(loginValue);
        String accessToken = jwtFactory.generateToken(member.getName());
        return LoginResponse.from(accessToken);
    }

    @ApiOperation("자기 자신의 정보를 조회합니다.")
    @GetMapping("/members/me")
    public MemberResponse getMe(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                @RequestHeader(name = "Authorization") String authorization,
                                HttpServletRequest request) {
        this.checkAuthority(request);
        Member member = memberService.getMemberByName((String) request.getAttribute("name"));
        return MemberResponse.from(member);
    }

    /**
     * 출석 체크를 요청합니다
     * - 성공하는 경우 200
     * - 이미 출석체크 한 경우 204
     * - 현재 출석할 수 있는 세션이 존재하지 않는 경우 400
     * - 회원 정보가 정확하지 않은 경우(이름) 401
     * - 거리가 너무 멀어서 출석 요청이 거절된 경우 403
     */
    @ApiOperation("출석 체크를 요청합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "출석 체크 성공"),
            @ApiResponse(code = 204, message = "이미 출석 체크 한 경우"),
            @ApiResponse(code = 400, message = "현재 출석할 수 있는 세션이 존재하지 않는 경우"),
            @ApiResponse(code = 401, message = "회원 정보가 정확하지 않은 경우"),
            @ApiResponse(code = 403, message = "거리가 너무 멀어서 출석 요청이 거절된 경우")
    })
    @PostMapping("/members/me/attend")
    public MemberAttendanceResponse attend(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                           @RequestHeader(name = "Authorization") String authorization,
                                           @RequestBody MemberAttendRequest memberAttendRequest,
                                           HttpServletRequest request) {
        checkAuthority(request);

        String name = memberAttendRequest.getName();
        Integer memberId = memberService.getMemberByName(name).getId();
        // 출석체크 요청
        AttendanceValue attendanceValue = AttendanceValue.of(
                memberId,
                memberAttendRequest.getLatitude(),
                memberAttendRequest.getLongitude()
        );
        Session session = sessionService.getCurrentSession().orElseThrow(BadRequestException::new);
        Attendance attendance = attendanceService.attend(attendanceValue);
        return MemberAttendanceResponse.of(attendance, session);
    }

    @ApiOperation("내 출석 정보를 조회합니다. (아직 구현되지 않았습니다)")
    @GetMapping("/members/me/attendances")
    public List<MemberAttendanceResponse> getAttendances(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                                         @RequestHeader(name = "Authorization") String authorization,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "20") Integer size) {
        return Collections.emptyList();
    }

    /**
     * 회원이 존재하는지 검사합니다.
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
    }
}
