package com.depromeet.deprocheck.deprocheckapi.service;

import com.depromeet.deprocheck.deprocheckapi.domain.Member;
import com.depromeet.deprocheck.deprocheckapi.vo.LoginValue;

public interface LoginService {
    Member login(LoginValue loginValue);
}
