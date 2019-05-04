package com.depromeet.deprocheck.deprocheckapi.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByFromAtLessThanAndToAtGreaterThan(ZonedDateTime fromAt, ZonedDateTime toAt);
}
