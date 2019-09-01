package com.depromeet.deprocheck.deprocheckapi.helper;

import com.depromeet.deprocheck.deprocheckapi.ui.dto.LoginRequest;
import org.springframework.test.util.ReflectionTestUtils;

public final class TestHelper {
    private TestHelper() {
    }

    public static LoginRequest createLoginRequest(String name) {
        LoginRequest loginRequest = new LoginRequest();
        ReflectionTestUtils.setField(loginRequest, "name", name);
        return loginRequest;
    }
}
