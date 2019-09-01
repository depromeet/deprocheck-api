package com.depromeet.deprocheck.deprocheckapi.domain.exception;

/**
 * 403
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
