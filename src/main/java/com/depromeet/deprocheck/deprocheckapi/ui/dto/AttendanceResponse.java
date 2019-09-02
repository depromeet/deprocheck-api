package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("session")
    private SessionResponse sessionResponse;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+09:00")
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT+09:00")
    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
