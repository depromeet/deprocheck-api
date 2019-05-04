package com.depromeet.deprocheck.deprocheckapi.dto;

import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private Integer id;
    /**
     * 기수
     */
    private Integer termNumber;
    /**
     * 직군
     */
    private JobGroup jobGroup;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getTermNumber(),
                member.getJobGroup()
        );
    }
}
