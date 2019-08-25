package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.dto.SessionResponse;
import com.depromeet.deprocheck.deprocheckapi.service.SessionService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping("/sessions")
    public List<SessionResponse> getSessions(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                             @RequestHeader(name = "Authorization") String authorization) {
        return sessionService.getAllSessions();
    }
}
