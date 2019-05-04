package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;

import java.util.Optional;

public interface SessionService {
    Session createSession();
    Optional<Session> getCurrentSession();
}
