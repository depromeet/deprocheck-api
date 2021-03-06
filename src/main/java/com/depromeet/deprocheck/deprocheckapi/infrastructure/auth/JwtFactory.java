package com.depromeet.deprocheck.deprocheckapi.infrastructure.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFactory {
    private static final String MEMBER_ID = "memberId";
    private static final String HEADER_PREFIX = "Bearer ";

    private final JWTVerifier jwtVerifier;
    private final String tokenIssuer;
    private final String tokenSigningKey;

    /**
     * jwt 를 생성합니다.
     */
    public String generateToken(Integer memberId) {
        String token;

        token = JWT.create()
                .withIssuer(tokenIssuer)
                .withClaim(MEMBER_ID, memberId)
                .sign(Algorithm.HMAC256(tokenSigningKey));
        log.info("token -- " + token);
        return token;
    }

    /**
     * 인증 헤더에서 유저 이름을 추출합니다.
     */
    public Optional<Integer> getId(String authorizationHeader) {
        if (StringUtils.isEmpty(authorizationHeader)) {
            return Optional.empty();
        }
        return this.decodeToken(authorizationHeader);
    }

    private Optional<Integer> decodeToken(String header) {
        String token;
        try {
            token = this.extractToken(header);
        } catch (IllegalArgumentException ex) {
            log.warn("Failed to extract token from header. header:" + header, ex);
            return Optional.empty();
        }

        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

        Map<String, Claim> claims = decodedJWT.getClaims();
        Claim idClaim = claims.get(MEMBER_ID);
        if (idClaim == null) {
            log.warn("Failed to decode jwt token. header:" + header);
            return Optional.empty();
        }
        return Optional.ofNullable(idClaim.asInt());
    }

    private String extractToken(String header) {
        if (StringUtils.isEmpty(header)) {
            throw new IllegalArgumentException("Authorization header 가 없습니다.");
        }
        if (header.length() < HEADER_PREFIX.length()) {
            throw new IllegalArgumentException("Authorization header size가 옳지 않습니다. header의 길이는 " + HEADER_PREFIX.length() + " 보다 크거나 같아야 합니다.");
        }
        if (!HEADER_PREFIX.equalsIgnoreCase(header.substring(0, HEADER_PREFIX.length()))) {
            throw new IllegalArgumentException("올바른 header 형식이 아닙니다. " + HEADER_PREFIX + "로 시작해야 합니다. ");
        }
        return header.substring(HEADER_PREFIX.length());
    }
}


