package com.depromeet.deprocheck.deprocheckapi.domain.exception;

/**
 * 404
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
