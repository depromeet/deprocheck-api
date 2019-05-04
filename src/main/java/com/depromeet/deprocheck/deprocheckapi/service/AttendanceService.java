package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.vo.AttendanceValue;

public interface AttendanceService {
    Attendance attend(AttendanceValue attendanceValue);
}
