package com.depromeet.deprocheck.deprocheckapi.domain.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.UnauthorizedException;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.MemberRepository;
import com.depromeet.deprocheck.deprocheckapi.domain.service.LoginService;
import com.depromeet.deprocheck.deprocheckapi.domain.vo.LoginValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final MemberRepository memberRepository;

    @Override
    public Member login(LoginValue loginValue) {
        Assert.notNull(loginValue, "'loginValue' must not be null");

        Authority authority = loginValue.getAuthority();
        switch (authority) {
            case ADMIN:
                return loginAdmin(loginValue.getName());
            case MEMBER:
                return loginMember(loginValue.getName());
            case LEAVER:
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("'authority' is not supported. authority:" + authority);
        }
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
