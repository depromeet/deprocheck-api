package com.depromeet.deprocheck.deprocheckapi.domain.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByFromAtLessThanAndToAtGreaterThan(LocalDateTime fromAt, LocalDateTime toAt);

    Optional<Session> findByDate(LocalDateTime localDateTime);
}
