package com.depromeet.deprocheck.deprocheckapi.domain;

public enum Authority {
    ADMIN, MEMBER, LEAVER, UNKNOWN;

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
