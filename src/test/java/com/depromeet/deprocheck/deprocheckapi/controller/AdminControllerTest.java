package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.dto.LoginRequest;
import com.depromeet.deprocheck.deprocheckapi.helper.TestHelper;
import com.depromeet.deprocheck.deprocheckapi.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void 관리자_로그인_성공() throws Exception {
        // given
        Member member = new Member();
        member.setAuthority(Authority.ADMIN);
        member.setName(ADMIN_NAME);
        member.setJobGroup(JobGroup.UNKNOWN);
        memberRepository.save(member);
        // when
        LoginRequest loginRequest = TestHelper.createLoginRequest("디프만");
        mockMvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(loginRequest)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }
}
