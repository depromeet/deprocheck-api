package com.depromeet.deprocheck.deprocheckapi.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static GeoLocation of(Double latitude, Double longitude) {
        GeoLocation geoLocation = new GeoLocation();
        geoLocation.latitude = latitude;
        geoLocation.longitude = longitude;
        return geoLocation;
    }
}
