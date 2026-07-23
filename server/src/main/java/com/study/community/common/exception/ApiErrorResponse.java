package com.study.community.common.exception;

import java.time.Instant;
import java.util.Map;

public record ApiErrorResponse(
        String code,
        String message,
        Map<String, String> fieldErrors,
        String path,
        Instant timestamp
) {

    public static ApiErrorResponse of(
            String code,
            String message,
            Map<String, String> fieldErrors,
            String path
    ) {
        return new ApiErrorResponse(code, message, fieldErrors, path, Instant.now());
    }
}

