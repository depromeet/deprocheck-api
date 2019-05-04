package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.dto.LoginRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.MemberResponse;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionCreateRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionResponse;
import com.depromeet.deprocheck.deprocheckapi.dto.admin.AdminAttendanceResponse;
import com.depromeet.deprocheck.deprocheckapi.exception.ForbiddenException;
import com.depromeet.deprocheck.deprocheckapi.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.utils.SessionUtils;
import com.depromeet.deprocheck.deprocheckapi.vo.LoginValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자가 사용할 수 있는 api 입니다
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;
    private final LoginService loginService;

    /**
     * 관리자 로그인 (이름 필드에 특정 비밀번호를 저장합니다)
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest,
                        HttpServletRequest request) {
        // 로그인되어있으면 지나가기
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.getAttribute("name");
        }

        LoginValue loginValue = LoginValue.of(
                loginRequest.getName(),
                Authority.ADMIN
        );
        Member member = loginService.login(loginValue);
        httpSession = request.getSession(true);
        httpSession.setAttribute("id", member.getId());
        httpSession.setAttribute("name", member.getName());
        return member.getName();
    }

    /**
     * 세션 정보를 생성합니다
     */
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponse createSession(@RequestBody SessionCreateRequest sessionCreateRequest,
                                         HttpServletRequest request) {
        checkAuthority(request);

        // TODO: 세션 정보 생성

        return null;
    }

    /**
     * 출석 정보를 조회합니다.
     */
    @GetMapping("/attendances")
    public List<AdminAttendanceResponse> getAttendances(@RequestParam String date,
                                                        HttpServletRequest request) {
        checkAuthority(request);

        // TODO: 세션별 출석 정보 조회

        return Collections.emptyList();
    }

    /**
     * 회원을 추가합니다.
     */
    @PostMapping("/members")
    public MemberResponse createMember(HttpServletRequest request) {
        checkAuthority(request);

        // TODO: 회원 한 명 추가

        return null;
    }

    /**
     * 회원 정보를 조회합니다.
     */
    @GetMapping("/members")
    public List<MemberResponse> getMembers(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "20") Integer size,
                                           HttpServletRequest request) {
        checkAuthority(request);

        Pageable pageable = PageRequest.of(page, size);
        return memberService.getMembers(pageable)
                .stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    // TODO: 관리자 비밀번호 변경 기능 추가

    /**
     * 세션이 존재하고, 관리자 권한을 가졌는지 검사합니다.
     */
    private void checkAuthority(HttpServletRequest request) {
        SessionUtils.getMemberName(request).orElseThrow(UnauthorizedException::new);
        if (!SessionUtils.isAdmin(request)) {
            throw new ForbiddenException();
        }
    }
}
