package com.depromeet.deprocheck.deprocheckapi.domain.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.domain.vo.LoginValue;

public interface LoginService {
    Member login(LoginValue loginValue);
}
