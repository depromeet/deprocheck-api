package com.depromeet.deprocheck.deprocheckapi.domain.service.impl;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.exception.NotFoundException;
import com.depromeet.deprocheck.deprocheckapi.domain.repository.MemberRepository;
import com.depromeet.deprocheck.deprocheckapi.domain.service.MemberService;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member createMember(MemberCreateRequest memberCreateRequest) {
        Assert.notNull(memberCreateRequest, "'memberCreateRequest' must not be null");

        Member member = Member.create(
                memberCreateRequest.getName(),
                memberCreateRequest.getTermNumber(),
                memberCreateRequest.getJobGroup(),
                memberCreateRequest.getAuthority()
        );
        return memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberByName(String name) {
        Assert.notNull(name, "'name' must not be null");
        return memberRepository.findByName(name)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMemberById(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::new);
    }
}
