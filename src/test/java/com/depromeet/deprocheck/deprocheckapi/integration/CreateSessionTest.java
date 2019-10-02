package com.depromeet.deprocheck.deprocheckapi.integration;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.MemberRepository;
import com.depromeet.deprocheck.deprocheckapi.helper.AdminControllerApi;
import com.depromeet.deprocheck.deprocheckapi.helper.SessionTestable;
import com.depromeet.deprocheck.deprocheckapi.helper.TestApiResultHelper;
import com.depromeet.deprocheck.deprocheckapi.infrastructure.auth.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.SessionCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class CreateSessionTest implements SessionTestable {
    private static final String ADMIN_NAME = "디프만";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @SpyBean
    private JwtFactory jwtFactory;

    private AdminControllerApi adminControllerApi;
    private Member member;

    @Before
    public void setUp() {
        this.adminControllerApi = AdminControllerApi.of(mockMvc, objectMapper);

        member = new Member();
        member.setTermNumber(1);
        member.setAuthority(Authority.ADMIN);
        member.setName(ADMIN_NAME);
        member.setJobGroup(JobGroup.UNKNOWN);
        memberRepository.save(member);

        when(jwtFactory.getId("Bearer accessToken")).thenReturn(Optional.of(member.getId()));
    }

    @Test
    public void 세션_생성__성공() throws Exception {
        // given
        String accessToken = "accessToken";
        SessionCreateRequest sessionCreateRequest = this.createSessionCreateRequest(
                "디캠프",
                "오리엔테이션",
                LocalDateTime.of(2019, 9, 28, 0, 0),
                LocalDateTime.of(2019, 9, 28, 14, 0),
                LocalDateTime.of(2019, 9, 28, 18, 0),
                37.4982271,
                127.0463081
        );
        // when
        TestApiResultHelper createResult = adminControllerApi.createSession(accessToken, sessionCreateRequest);
        // then
        assertThat(createResult.is2xxSuccessful()).isTrue();
    }

    @Test
    public void 같은_날짜에_세션을_두_개_이상_생성할_수_없음() throws Exception {
        // given
        String accessToken = "accessToken";
        SessionCreateRequest sessionCreateRequest = this.createSessionCreateRequest(
                "디캠프",
                "오리엔테이션",
                LocalDateTime.of(2019, 9, 28, 0, 0),
                LocalDateTime.of(2019, 9, 28, 14, 0),
                LocalDateTime.of(2019, 9, 28, 18, 0),
                37.4982271,
                127.0463081
        );
        // when 1
        TestApiResultHelper firstCreateResult = adminControllerApi.createSession(accessToken, sessionCreateRequest);
        // then 1
        assertThat(firstCreateResult.is2xxSuccessful()).isTrue();

        // when 2
        TestApiResultHelper secondCreateResult = adminControllerApi.createSession(accessToken, sessionCreateRequest);
        // then 2
        assertThat(secondCreateResult.is4xxClientError()).isTrue();
    }
}
