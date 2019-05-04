package com.depromeet.deprocheck.deprocheckapi.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.repository.SessionRepository;
import com.depromeet.deprocheck.deprocheckapi.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public Session createSession() {
        return null;
    }

    /**
     * 현재 시간을 기준으로, 유효한 세션을 조회합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Session> getCurrentSession() {
        ZonedDateTime now = ZonedDateTime.now();
        return sessionRepository.findByFromAtLessThanAndToAtGreaterThan(now, now);
    }
}
