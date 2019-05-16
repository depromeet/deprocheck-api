package com.depromeet.deprocheck.deprocheckapi.dto;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private LocalDateTime date;
    /**
     * 시작 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private LocalDateTime from;
    /**
     * 끝나는 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "GMT+09:00")
    private LocalDateTime to;

    public static SessionResponse from(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getName(),
                session.getAddress(),
                session.getDate(),
                session.getFromAt(),
                session.getToAt()
        );
    }
}
