package com.depromeet.deprocheck.deprocheckapi.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByMemberIdAndSessionId(Integer memberId, Integer sessionId);
}
