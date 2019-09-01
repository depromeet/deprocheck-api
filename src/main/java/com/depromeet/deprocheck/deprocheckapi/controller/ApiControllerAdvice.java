package com.depromeet.deprocheck.deprocheckapi.controller;

import com.depromeet.deprocheck.deprocheckapi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleNoContentException(NoContentException ex) {
        log.warn("NoContentException", ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException ex) {
        log.warn("BadRequestException", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException ex) {
        log.warn("UnauthorizedException", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenException(ForbiddenException ex) {
        log.warn("ForbiddenException", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        log.warn("NotFoundException", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex) {
        log.error("Exception", ex);
        return ex.getMessage();
    }
}
