package com.mini.api.dto;

import org.springframework.http.HttpStatus;

public class ApiError {

    private int status;

    private String message;

    public ApiError(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(), status);
    }

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}