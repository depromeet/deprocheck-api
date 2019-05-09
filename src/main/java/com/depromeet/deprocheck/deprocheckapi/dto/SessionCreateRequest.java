package com.depromeet.deprocheck.deprocheckapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private ZonedDateTime date;
    /**
     * 시작 시간 (보통 14시)
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private ZonedDateTime from;
    /**
     * 끝나는 시간 (보통 18시)
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private ZonedDateTime to;
    /**
     * 위도
     */
    private Double latitude;
    /**
     * 경도
     */
    private Double longitude;

}
