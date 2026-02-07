package com.study.shortJ.exception;

public class UrlMappingNotFoundOrExpiredException extends RuntimeException{
    public UrlMappingNotFoundOrExpiredException(String message) {
        super(message);
    }
}
