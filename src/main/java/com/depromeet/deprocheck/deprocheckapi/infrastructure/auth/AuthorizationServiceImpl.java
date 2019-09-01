package com.depromeet.deprocheck.deprocheckapi.infrastructure.auth;

import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationResult;
import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationService;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final JwtFactory jwtFactory;

    @Override
    public AuthorizationResult authorize(String header) {
        return jwtFactory.getId(header)
                .map(AuthorizationResult::from)
                .orElseThrow(() -> new UnauthorizedException("토큰이 유효하지 않습니다."));
    }
}
