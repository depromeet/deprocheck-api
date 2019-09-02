package com.depromeet.deprocheck.deprocheckapi.application.assembler;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.AttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceAssembler {
    private final SessionAssembler sessionAssembler;

    public AttendanceResponse toAttendanceResponse(Attendance attendance) {
        if (attendance == null) {
            return null;
        }
        AttendanceResponse attendanceResponse = new AttendanceResponse();
        attendanceResponse.setId(attendance.getId());
        attendanceResponse.setSessionResponse(sessionAssembler.toSessionResponse(attendance.getSession()));
        attendanceResponse.setCreatedAt(attendance.getCreatedAt());
        attendanceResponse.setUpdatedAt(attendance.getUpdatedAt());
        return attendanceResponse;
    }
}
