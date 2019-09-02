package com.depromeet.deprocheck.deprocheckapi.domain.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.vo.AttendanceValue;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttendanceService {
    Attendance attend(AttendanceValue attendanceValue);

    List<Attendance> getAttendances(Integer memberId, Pageable pageable);
}
