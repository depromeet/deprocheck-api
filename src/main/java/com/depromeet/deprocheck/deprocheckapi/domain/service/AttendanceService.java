package com.depromeet.deprocheck.deprocheckapi.domain.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.vo.AttendanceValue;

public interface AttendanceService {
    Attendance attend(AttendanceValue attendanceValue);
}
