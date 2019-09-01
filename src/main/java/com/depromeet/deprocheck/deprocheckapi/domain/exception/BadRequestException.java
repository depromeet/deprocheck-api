package com.depromeet.deprocheck.deprocheckapi.domain.exception;

/**
 * 400
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
