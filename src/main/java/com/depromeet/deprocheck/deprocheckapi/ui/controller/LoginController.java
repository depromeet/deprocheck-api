package com.depromeet.deprocheck.deprocheckapi.ui.controller;

import com.depromeet.deprocheck.deprocheckapi.domain.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.LoginRequest;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(value = "로그인", description = "로그인 요청입니다. 인증이 필요하지 않습니다.")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://check.depromeet.com"
})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    @ApiOperation("멤버 이름을 입력해서 로그인합니다. 인증이 필요하지 않은 요청입니다. 회원 권한에 관계없이 모두 사용 가능합니다. 이미 로그인되어있는 경우에도, 새 로그인 정보에 맞는 토큰을 발급합니다. ")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String accessToken = loginService.login(loginRequest.getName());
        return LoginResponse.from(accessToken);
    }
}
