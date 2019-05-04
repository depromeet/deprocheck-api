package com.depromeet.deprocheck.deprocheckapi.dto.member;

import com.depromeet.deprocheck.deprocheckapi.domain.Attendance;
import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberAttendanceResponse {
    private Integer id;
    /**
     * 출석한 세션 정보
     */
    @JsonProperty("session")
    private SessionResponse sessionResponse;


    public static MemberAttendanceResponse of(Attendance attendance, Session session) {
        return new MemberAttendanceResponse(
                attendance.getId(),
                SessionResponse.from(session)
        );
    }
}
