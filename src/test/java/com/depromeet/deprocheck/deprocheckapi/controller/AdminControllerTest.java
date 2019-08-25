package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.component.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.dto.LoginRequest;
import com.depromeet.deprocheck.deprocheckapi.dto.SessionCreateRequest;
import com.depromeet.deprocheck.deprocheckapi.helper.TestHelper;
import com.depromeet.deprocheck.deprocheckapi.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AdminControllerTest {
    private static final String ADMIN_NAME = "디프만";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @SpyBean
    private JwtFactory jwtFactory;

    @Before
    public void setup() {
        Member member = new Member();
        member.setTermNumber(1);
        member.setAuthority(Authority.ADMIN);
        member.setName(ADMIN_NAME);
        member.setJobGroup(JobGroup.UNKNOWN);
        memberRepository.save(member);
    }

    @Test
    public void 관리자_로그인_성공() throws Exception {
        // given
        // when
        LoginRequest loginRequest = TestHelper.createLoginRequest("디프만");
        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginRequest)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    public void 세션_생성() throws Exception {
        // given
        when(jwtFactory.getName("Bearer accessToken")).thenReturn(Optional.of(ADMIN_NAME));
        // when
        SessionCreateRequest sessionCreateRequest = this.createSessionCreateRequest(
                "디캠프",
                "오리엔테이션",
                LocalDateTime.of(2019, 9, 28, 0, 0),
                LocalDateTime.of(2019, 9, 28, 14, 0),
                LocalDateTime.of(2019, 9, 28, 18, 0),
                37.4982271,
                127.0463081
        );
        mockMvc.perform(post("/api/admin/sessions")
                .header("Authorization", "Bearer accessToken")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(sessionCreateRequest)))
                // then
                .andExpect(status().isCreated())
                .andDo(print());
    }

    private SessionCreateRequest createSessionCreateRequest(
            String address,
            String name,
            LocalDateTime date,
            LocalDateTime from,
            LocalDateTime to,
            Double latitude,
            Double longitude) {
        SessionCreateRequest sessionCreateRequest = new SessionCreateRequest();
        ReflectionTestUtils.setField(sessionCreateRequest, "address", address);
        ReflectionTestUtils.setField(sessionCreateRequest, "name", name);
        ReflectionTestUtils.setField(sessionCreateRequest, "date", date);
        ReflectionTestUtils.setField(sessionCreateRequest, "from", from);
        ReflectionTestUtils.setField(sessionCreateRequest, "to", to);
        ReflectionTestUtils.setField(sessionCreateRequest, "latitude", latitude);
        ReflectionTestUtils.setField(sessionCreateRequest, "longitude", longitude);
        return sessionCreateRequest;
    }
}
