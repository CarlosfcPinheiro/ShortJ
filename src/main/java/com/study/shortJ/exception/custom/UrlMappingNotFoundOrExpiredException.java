package com.study.shortJ.exception.custom;

public class UrlMappingNotFoundOrExpiredException extends RuntimeException{
    public UrlMappingNotFoundOrExpiredException(String message) {
        super(message);
    }
}
