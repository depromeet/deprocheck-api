package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionCreateRequest;

import java.util.Optional;

public interface SessionService {
    Session createSession(SessionCreateRequest sessionCreateRequest);
    Optional<Session> getCurrentSession();
}
