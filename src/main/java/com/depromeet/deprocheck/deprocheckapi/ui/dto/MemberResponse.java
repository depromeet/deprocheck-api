package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import lombok.*;

@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    /**
     * 이름
     */
    private String name;
    /**
     * 권한
     */
    private Authority authority;
}
