package com.depromeet.deprocheck.deprocheckapi.ui.dto;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import com.depromeet.deprocheck.deprocheckapi.domain.JobGroup;
import lombok.Data;

@Data
public class MemberCreateRequest {
    private String name;
    private Integer termNumber;
    private JobGroup jobGroup;
    private Authority authority;
}
