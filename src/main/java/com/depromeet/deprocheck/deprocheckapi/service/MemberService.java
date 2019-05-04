package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberService {
    Member createMember();

    List<Member> getMembers(Pageable pageable);

    Member getMemberByName(String name);

    Member getMemberById(Integer memberId);
}
