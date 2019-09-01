package com.depromeet.deprocheck.deprocheckapi.infrastructure.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.depromeet.deprocheck.deprocheckapi.application.JwtFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:jwt.properties")
public class JwtConfig {

    @Value("${jwt.tokenIssuer:tokenIssuer}")
    private String tokenIssuer;

    @Value("${jwt.tokenSigningKey:tokenSigningKey}")
    private String tokenSigningKey;

    @Bean
    public JwtFactory jwtFactory() {
        return new JwtFactory(
                JWT.require(Algorithm.HMAC256(tokenSigningKey)).build(),
                tokenIssuer,
                tokenSigningKey
        );
    }
}
