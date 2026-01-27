package com.study.shortJ.mapper;

import com.study.shortJ.config.UrlShortenerConfig;
import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.model.UrlMapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UrlMappingMapper {

    private final UrlShortenerConfig urlShortenerConfig;

    public UrlMappingMapper(UrlShortenerConfig urlShortenerConfig) {
        this.urlShortenerConfig = urlShortenerConfig;
    }

    public UrlMapping toEntity(CreateShortUrlDTO createShortUrlDTO) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(createShortUrlDTO.getOriginalUrl());
        urlMapping.setAlias(createShortUrlDTO.getAlias());
        urlMapping.setExpirationDate(LocalDateTime.now()
                .plusMinutes(createShortUrlDTO.getExpirationMinutes() != null ? createShortUrlDTO.getExpirationMinutes() : urlShortenerConfig.getStdExpirationMinutes())
        );
        return urlMapping;
    }
}
