package com.depromeet.deprocheck.deprocheckapi.vo;

import lombok.Value;

@Value(staticConstructor = "of")
public class AttendanceValue {
    private final Integer memberId;
    private final Double latitude;
    private final Double longitude;
}
