package com.depromeet.deprocheck.deprocheckapi.dto.admin;

import com.depromeet.deprocheck.deprocheckapi.dto.MemberResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class AdminAttendanceResponse {
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private ZonedDateTime createdAt;
    /**
     * 출석한 회원의 정보
     */
    @JsonProperty("member")
    private MemberResponse memberResponse;
}
