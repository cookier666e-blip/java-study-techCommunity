package com.study.community.common.exception;

public class DuplicateUserException extends RuntimeException {

    private final String code;

    public DuplicateUserException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

