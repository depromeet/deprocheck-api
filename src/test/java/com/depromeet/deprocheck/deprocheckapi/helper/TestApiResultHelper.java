package com.depromeet.deprocheck.deprocheckapi.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.io.IOException;

public class TestApiResultHelper {
    private static final Logger log = LoggerFactory.getLogger(TestApiResultHelper.class);

    private final MvcResult mvcResult;
    private final ObjectMapper objectMapper;

    private TestApiResultHelper(MvcResult mvcResult, ObjectMapper objectMapper) {
        this.mvcResult = mvcResult;
        this.objectMapper = objectMapper;
    }

    public static TestApiResultHelper of(MvcResult mvcResult, ObjectMapper objectMapper) {
        return new TestApiResultHelper(mvcResult, objectMapper);
    }

    public boolean is2xxSuccessful() {
        return HttpStatus.valueOf(mvcResult.getResponse().getStatus()).is2xxSuccessful();
    }

    public boolean is4xxClientError() {
        return HttpStatus.valueOf(mvcResult.getResponse().getStatus()).is4xxClientError();
    }

    public boolean is5xxServerError() {
        return HttpStatus.valueOf(mvcResult.getResponse().getStatus()).is5xxServerError();
    }

    public <T> T getBody(Class<T> clazz) {
        String contentType = mvcResult.getResponse().getContentType();
        Assert.notNull(contentType, "'contentType' must not be null");

        if (!MediaType.valueOf(contentType).isCompatibleWith(MediaType.APPLICATION_JSON)) {
            log.error("ContentType is not supported. contentType:{}", contentType);
            return null;
        }
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), clazz);
        } catch (IOException e) {
            log.warn("IOException", e);
            return null;
        }
    }
}
