package com.study.shortJ.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortUrlDTO {
    private String originalUrl;

    @JsonProperty("custom_alias")
    private String alias;

    private Long expirationMinutes;
}
