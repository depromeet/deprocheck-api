package com.depromeet.deprocheck.deprocheckapi.exception;

/**
 * 401
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
