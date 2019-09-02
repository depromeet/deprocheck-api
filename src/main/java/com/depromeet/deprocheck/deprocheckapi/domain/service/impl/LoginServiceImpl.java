package com.depromeet.deprocheck.deprocheckapi.domain.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.MemberRepository;
import com.depromeet.deprocheck.deprocheckapi.domain.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.infrastructure.auth.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;
    private final JwtFactory jwtFactory;

    @Override
    @Transactional(readOnly = true)
    public String login(String name) {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new UnauthorizedException("Member not found. name:" + name));
        return jwtFactory.generateToken(member.getId());
    }

    private Member loginAdmin(String name) {
        return memberRepository.findByNameAndAuthority(name, Authority.ADMIN)
                .orElseThrow(UnauthorizedException::new);
    }

    /**
     * 이름이 같은 멤버는 없다고 가정합니다.
     */
    private Member loginMember(String name) {
        return memberRepository.findByNameAndAuthority(name, Authority.MEMBER)
                .orElseThrow(UnauthorizedException::new);
    }
}
