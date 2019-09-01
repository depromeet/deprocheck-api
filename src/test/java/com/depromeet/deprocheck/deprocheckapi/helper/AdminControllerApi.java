package com.depromeet.deprocheck.deprocheckapi.helper;

import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public final class AdminControllerApi {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    private AdminControllerApi(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public static AdminControllerApi of(MockMvc mockMvc, ObjectMapper objectMapper) {
        return new AdminControllerApi(mockMvc, objectMapper);
    }

    public TestApiResultHelper createMember(String accessToken, MemberCreateRequest memberCreateRequest) throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/admin/members")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(memberCreateRequest)))
                .andDo(print())
                .andReturn();
        return TestApiResultHelper.of(mvcResult, objectMapper);
    }
}
