package com.depromeet.deprocheck.deprocheckapi.ui.controller;

import com.depromeet.deprocheck.deprocheckapi.domain.service.SessionService;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.SessionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "세션", description = "세션 관련 요청입니다. 인증이 필요합니다. ")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @ApiOperation("세션 정보를 조회합니다. ")
    @GetMapping("/sessions")
    public List<SessionResponse> getSessions(@ApiParam(name = "Authorization", value = "Bearer {accessToken}", required = true)
                                             @RequestHeader(name = "Authorization") String authorization) {
        return sessionService.getAllSessions();
    }
}
