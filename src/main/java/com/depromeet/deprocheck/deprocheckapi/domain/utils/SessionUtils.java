package com.depromeet.deprocheck.deprocheckapi.domain.utils;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SessionUtils {
    private SessionUtils() {

    }

    /**
     * 세션에 저장된 회원의 id를 가져옵니다.
     */
    public static Optional<Integer> getMemberId(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(req -> req.getSession(false))
                .map(httpSession -> httpSession.getAttribute("id"))
                .map(Integer.class::cast);
    }

    /**
     * 세션에 저장된 이름을 가져옵니다.
     */
    public static Optional<String> getMemberName(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(req -> req.getSession(false))
                .map(httpSession -> httpSession.getAttribute("name"))
                .map(String::valueOf);
    }

    /**
     * 관리자 권한이 있는 세션인지 판단합니다.
     */
    public static boolean isAdmin(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(req -> req.getSession(false))
                .map(httpSession -> httpSession.getAttribute("authority"))
                .filter(Authority.ADMIN::equals)
                .isPresent();
    }
}
