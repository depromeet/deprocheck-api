package com.depromeet.deprocheck.deprocheckapi.application.assembler;

import com.depromeet.deprocheck.deprocheckapi.domain.Session;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.SessionResponse;
import org.springframework.stereotype.Component;

@Component
public class SessionAssembler {
    public SessionResponse toSessionResponse(Session session) {
        if (session == null) {
            return null;
        }
        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setId(session.getId());
        sessionResponse.setName(session.getName());
        sessionResponse.setAddress(session.getAddress());
        sessionResponse.setDate(session.getDate());
        sessionResponse.setFrom(session.getFromAt());
        sessionResponse.setTo(session.getToAt());
        return sessionResponse;
    }
}
