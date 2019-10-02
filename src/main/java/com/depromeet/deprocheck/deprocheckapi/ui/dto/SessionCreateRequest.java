package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionCreateRequest {
    /**
     * 주소
     */
    private String address;
    /**
     * 세션 이름 (1주차, 2주차, ..)
     */
    private String name;
    /**
     * 날짜 (0시 0분 0초로 끝나야함)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;
    /**
     * 시작 시간 (보통 14시)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime from;
    /**
     * 끝나는 시간 (보통 18시)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime to;
    /**
     * 위도
     */
    private Double latitude;
    /**
     * 경도
     */
    private Double longitude;

}
