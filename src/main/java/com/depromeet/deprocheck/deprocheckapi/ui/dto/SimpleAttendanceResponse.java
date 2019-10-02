package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleAttendanceResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("member")
    private MemberResponse memberResponse;
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    @JsonProperty("updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
}
