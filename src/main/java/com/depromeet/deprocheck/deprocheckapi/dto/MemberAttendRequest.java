package com.depromeet.deprocheck.deprocheckapi.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberAttendRequest {
    /**
     * 출석하는 멤버의 이름
     */
    private String name;
    /**
     * 위도
     */
    private Double latitude;
    /**
     * 경도
     */
    private Double longitude;
}
