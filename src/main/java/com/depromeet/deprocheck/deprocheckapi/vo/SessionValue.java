package com.depromeet.deprocheck.deprocheckapi.vo;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class SessionValue {
    /**
     * 세션 이름 (ex, 1주차, 2주차, .. )
     */
    private final String name;
    /**
     * 세션 날짜
     */
    private final ZonedDateTime date;
    /**
     * 세션 시작 시간
     */
    private final ZonedDateTime from;
    /**
     * 세션 끝 시간
     */
    private final ZonedDateTime to;
}
