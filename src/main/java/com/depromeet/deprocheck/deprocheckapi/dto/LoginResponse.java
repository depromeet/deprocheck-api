package com.depromeet.deprocheck.deprocheckapi.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private String accessToken;

    public static LoginResponse from(String accessToken) {
        Assert.notNull(accessToken, "'accessToken' must not be null");
        return new LoginResponse(accessToken);
    }
}
