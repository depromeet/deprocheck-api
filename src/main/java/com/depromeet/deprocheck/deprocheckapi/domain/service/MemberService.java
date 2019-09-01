package com.depromeet.deprocheck.deprocheckapi.domain.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberCreateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    Member createMember(MemberCreateRequest memberCreateRequest);

    List<Member> getMembers(Pageable pageable);

    Member getMemberByName(String name);

    Member getMemberById(Integer memberId);
}
