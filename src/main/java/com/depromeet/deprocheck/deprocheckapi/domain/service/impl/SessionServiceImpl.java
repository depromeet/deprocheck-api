package com.depromeet.deprocheck.deprocheckapi.domain.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.GeoLocation;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.BadRequestException;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.SessionRepository;
import com.depromeet.deprocheck.deprocheckapi.domain.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.domain.utils.DateTimeUtils;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.SessionCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    @Transactional
    public Session createSession(SessionCreateRequest sessionCreateRequest) {
        // 유효성 검사
        Assert.notNull(sessionCreateRequest, "'sessionCreateRequest' must not be null");

        LocalDateTime date = DateTimeUtils.getStartTimeOfDay(sessionCreateRequest.getDate());
        if (sessionRepository.findByDate(date).isPresent()) {
            throw new BadRequestException("Failed to create session. Already exist session at date:" + date);
        }

        Session session = new Session();
        session.setAddress(sessionCreateRequest.getAddress());
        session.setName(sessionCreateRequest.getName());
        session.setDate(date);
        session.setFromAt(sessionCreateRequest.getFrom());
        session.setToAt(sessionCreateRequest.getTo());
        session.setGeoLocation(GeoLocation.of(
                sessionCreateRequest.getLatitude(),
                sessionCreateRequest.getLongitude()
        ));
        return sessionRepository.save(session);
    }

    /**
     * 현재 시간을 기준으로, 유효한 세션을 조회합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Session> getCurrentSession() {
        LocalDateTime now = LocalDateTime.now();
        return sessionRepository.findByFromAtLessThanAndToAtGreaterThan(now, now);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }
}
