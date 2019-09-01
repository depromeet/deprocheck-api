package com.depromeet.deprocheck.deprocheckapi.application.auth;

import lombok.Value;

@Value(staticConstructor = "from")
public class AuthorizationResult {
    private final Integer id;
}
