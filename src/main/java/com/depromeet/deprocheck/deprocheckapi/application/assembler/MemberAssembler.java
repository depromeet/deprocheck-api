package com.depromeet.deprocheck.deprocheckapi.application.assembler;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.ui.dto.MemberResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberAssembler {
    public MemberResponse toMemberResponse(Member member) {
        if (member == null) {
            return null;
        }
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .termNumber(member.getTermNumber())
                .jobGroup(member.getJobGroup())
                .authority(member.getAuthority())
                .build();
    }
}
