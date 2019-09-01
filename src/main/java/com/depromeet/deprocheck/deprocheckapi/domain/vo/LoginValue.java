package com.depromeet.deprocheck.deprocheckapi.domain.vo;

import com.depromeet.deprocheck.deprocheckapi.domain.Authority;
import lombok.Value;

@Value(staticConstructor = "of")
public class LoginValue {
    private final String name;
    private final Authority authority;
}
