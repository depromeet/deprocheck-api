package com.depromeet.deprocheck.deprocheckapi.ui.interceptor;


import com.depromeet.deprocheck.deprocheckapi.application.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtFactory jwtFactory;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        String name = jwtFactory.getName(token)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));
        request.setAttribute("name", name);
        return super.preHandle(request, response, handler);
    }
}
