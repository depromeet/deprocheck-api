package com.depromeet.deprocheck.deprocheckapi.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
