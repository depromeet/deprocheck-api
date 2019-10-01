package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionResponse {
    private Integer id;
    /**
     * 세션 이름
     */
    private String name;
    /**
     * 주소
     */
    private String address;
    /**
     * 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;
    /**
     * 시작 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime from;
    /**
     * 끝나는 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime to;
}
