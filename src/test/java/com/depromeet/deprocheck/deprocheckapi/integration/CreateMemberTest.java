package com.depromeet.deprocheck.deprocheckapi.integration;

import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationResult;
import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationService;
import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.MemberRepository;
import com.depromeet.deprocheck.deprocheckapi.helper.AdminControllerApi;
import com.depromeet.deprocheck.deprocheckapi.helper.TestApiResultHelper;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SuppressWarnings("NonAsciiCharacters")
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateMemberTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @MockBean
    private AuthorizationService authorizationService;

    private AdminControllerApi adminControllerApi;

    @Before
    public void setUp() {
        this.adminControllerApi = AdminControllerApi.of(mockMvc, objectMapper);

        when(authorizationService.authorize("Bearer accessToken"))
                .thenReturn(AuthorizationResult.from(1));

        Member member = Member.create("관리자", 0, JobGroup.DEVELOPER, Authority.ADMIN);
        ReflectionTestUtils.setField(member, "id", 1);
        memberRepository.save(member);
    }

    @Test
    public void 멤버_생성_성공() throws Exception {
        // given
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setName("전해성");
        memberCreateRequest.setTermNumber(1);
        memberCreateRequest.setAuthority(Authority.ADMIN);
        memberCreateRequest.setJobGroup(JobGroup.DEVELOPER);
        // when
        TestApiResultHelper createMemberResult = adminControllerApi.createMember("accessToken", memberCreateRequest);
        // then
        assertThat(createMemberResult.is2xxSuccessful()).isTrue();
    }
}
