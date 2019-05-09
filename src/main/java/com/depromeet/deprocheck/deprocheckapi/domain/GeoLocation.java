package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Value(staticConstructor = "of")
public class GeoLocation {
    /**
     * 위도
     */
    @Column
    private Double latitude;
    /**
     * 경도
     */
    @Column
    private Double longitude;
}
