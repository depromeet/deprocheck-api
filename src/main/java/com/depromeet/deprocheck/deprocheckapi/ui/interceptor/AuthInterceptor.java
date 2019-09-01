package com.depromeet.deprocheck.deprocheckapi.ui.interceptor;


import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationResult;
import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String ID = "id";
    private final AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        AuthorizationResult result = authorizationService.authorize(header);
        request.setAttribute(ID, result.getId());
        return super.preHandle(request, response, handler);
    }
}
