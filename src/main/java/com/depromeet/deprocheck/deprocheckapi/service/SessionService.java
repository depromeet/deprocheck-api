package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionCreateRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionResponse;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    Session createSession(SessionCreateRequest sessionCreateRequest);
    Optional<Session> getCurrentSession();
    List<SessionResponse> getAllSessions();
}
