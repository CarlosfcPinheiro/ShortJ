package com.study.shortJ.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "short-url")
public class ShortUrlConfig {
    private String allowedChars;
    private int keyLength;
    private int stdExpirationMinutes;
    private String hashSaltSecret;
}
