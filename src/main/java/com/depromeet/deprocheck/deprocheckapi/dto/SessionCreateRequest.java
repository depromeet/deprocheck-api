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
     * 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private ZonedDateTime date;
}
