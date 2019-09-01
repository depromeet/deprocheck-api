package com.depromeet.deprocheck.deprocheckapi.domain.repository;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByName(String name);

    Optional<Member> findByNameAndAuthority(String name, Authority authority);
}
