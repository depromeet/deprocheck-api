package com.depromeet.deprocheck.deprocheckapi.domain.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByMemberIdAndSessionId(Integer memberId, Integer sessionId);

    Page<Attendance> findByMemberId(Integer memberId, Pageable pageable);

    List<Attendance> findBySessionId(Integer sessionId);
}
