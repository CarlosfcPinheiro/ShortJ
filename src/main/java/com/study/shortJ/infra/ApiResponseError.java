package com.study.shortJ.infra;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseError {
    private int status;
    private final boolean success = false;
    private String message;

    public ApiResponseError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
