package com.depromeet.deprocheck.deprocheckapi.application.auth;

public interface AuthorizationService {
    AuthorizationResult authorize(String header);
}
