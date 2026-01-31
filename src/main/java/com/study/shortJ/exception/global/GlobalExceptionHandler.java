package com.study.shortJ.exception.global;

import com.study.shortJ.exception.custom.ResourceNotFoundException;
import com.study.shortJ.exception.model.ApiResponse;
import com.study.shortJ.exception.model.ApiResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGenericException(Exception e){
        logger.error("Unexpected error occurred", e);
        ApiResponseError response = new ApiResponseError(500, "Internal Server Error");
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ApiResponse response = new ApiResponse(e.getMessage(), true, 404, null);
        return ResponseEntity.status(404).body(response);
    }

}
