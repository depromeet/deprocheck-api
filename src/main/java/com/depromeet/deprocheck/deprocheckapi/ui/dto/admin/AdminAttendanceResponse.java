package com.depromeet.deprocheck.deprocheckapi.ui.dto.admin;

import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class AdminAttendanceResponse {
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    /**
     * 출석한 회원의 정보
     */
    @JsonProperty("member")
    private MemberResponse memberResponse;
}
