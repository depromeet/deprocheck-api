package com.depromeet.deprocheck.deprocheckapi.vo;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class SessionValue {
    /**
     * 세션 이름 (ex, 1주차, 2주차, .. )
     */
    private final String name;
    /**
     * 세션 날짜
     */
    private final LocalDateTime date;
    /**
     * 세션 시작 시간
     */
    private final LocalDateTime from;
    /**
     * 세션 끝 시간
     */
    private final LocalDateTime to;
}
