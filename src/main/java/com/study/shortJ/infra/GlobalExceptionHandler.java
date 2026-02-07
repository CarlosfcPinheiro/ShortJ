package com.study.shortJ.infra;

import com.study.shortJ.exception.ResourceNotFoundException;
import com.study.shortJ.exception.UrlMappingNotFoundOrExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle validation errors from @Valid annotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e) {
        ApiResponse response = new ApiResponse("Validation failed", false, 400);
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiResponseError> handleGenericException(Exception e){
        logger.error("Unexpected error occurred", e);
        ApiResponseError response = new ApiResponseError(500, "Internal Server Error");
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ApiResponse response = new ApiResponse(e.getMessage(), true, 404, null);
        return ResponseEntity.status(404).body(response);
    }

    // Redirect to a custom error page for URL not found or expired
    @ExceptionHandler(UrlMappingNotFoundOrExpiredException.class)
    private ModelAndView handleUrlMappingNotFoundOrExpiredException(UrlMappingNotFoundOrExpiredException e){
        return new ModelAndView("NotFoundOrExpired");
    }

}
