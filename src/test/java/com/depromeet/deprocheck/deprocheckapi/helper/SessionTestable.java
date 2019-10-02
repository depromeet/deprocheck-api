package com.depromeet.deprocheck.deprocheckapi.helper;

import com.depromeet.deprocheck.deprocheckapi.ui.dto.SessionCreateRequest;

import java.time.LocalDateTime;

public interface SessionTestable {
    default SessionCreateRequest createSessionCreateRequest(
            String address,
            String name,
            LocalDateTime date,
            LocalDateTime from,
            LocalDateTime to,
            Double latitude,
            Double longitude) {
        SessionCreateRequest sessionCreateRequest = new SessionCreateRequest();
        sessionCreateRequest.setAddress(address);
        sessionCreateRequest.setName(name);
        sessionCreateRequest.setDate(date);
        sessionCreateRequest.setFrom(from);
        sessionCreateRequest.setTo(to);
        sessionCreateRequest.setLatitude(latitude);
        sessionCreateRequest.setLongitude(longitude);
        return sessionCreateRequest;
    }
}
