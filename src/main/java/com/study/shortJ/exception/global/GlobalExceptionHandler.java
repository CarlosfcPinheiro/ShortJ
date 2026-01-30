package com.study.shortJ.exception.global;

import com.study.shortJ.exception.model.ApiResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGenericException(Exception e){
        ApiResponseError response = new ApiResponseError(400, e);
        return ResponseEntity.badRequest().body(response);
    }

}
