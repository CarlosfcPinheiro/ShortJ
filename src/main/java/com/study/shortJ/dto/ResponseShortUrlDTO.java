package com.study.shortJ.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseShortUrlDTO {
    private String originalUrl;
    private String shortUrl;
    private String alias;
    private LocalDateTime expirationDate;
}
