package com.study.shortJ.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private boolean success;
    private int statusCode;
    private Object data;

    public ApiResponse(String message, boolean success, int statusCode, Object data) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(String message, boolean success, int statusCode) {
        this.message = message;
        this.success = success;
        this.statusCode = statusCode;
    }

    public ApiResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }
}
