package com.study.shortJ.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "short-url")
public class UrlShortenerConfig {
    private String allowedChars;
    private int keyLength;
    private int StdExpirationMinutes;
}
