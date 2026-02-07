package com.study.shortJ.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortUrlDTO {
    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String originalUrl;

    @JsonProperty("customAlias")
    @Size(min = 4, max = 20, message = "Alias must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Alias can only contain alphanumeric characters, hyphens, and underscores")
    private String alias;
    private Long expirationMinutes;
}
