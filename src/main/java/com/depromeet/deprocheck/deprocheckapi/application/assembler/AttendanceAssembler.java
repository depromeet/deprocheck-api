package com.depromeet.deprocheck.deprocheckapi.application.assembler;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.AttendanceResponse;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.SimpleAttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AttendanceAssembler {
    private final SessionAssembler sessionAssembler;
    private final MemberAssembler memberAssembler;

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

    public SimpleAttendanceResponse toSimpleAttendanceResponse(Attendance attendance) {
        if (attendance == null) {
            return null;
        }
        SimpleAttendanceResponse simpleAttendanceResponse = new SimpleAttendanceResponse();
        simpleAttendanceResponse.setId(attendance.getId());
        simpleAttendanceResponse.setMemberResponse(memberAssembler.toMemberResponse(attendance.getMember()));
        simpleAttendanceResponse.setCreatedAt(attendance.getCreatedAt());
        simpleAttendanceResponse.setUpdatedAt(attendance.getUpdatedAt());
        return simpleAttendanceResponse;
    }
}
