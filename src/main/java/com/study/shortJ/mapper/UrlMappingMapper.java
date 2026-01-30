package com.study.shortJ.mapper;

import com.study.shortJ.config.ShortUrlConfig;
import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.model.UrlMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Component
public class UrlMappingMapper {

    private final ShortUrlConfig shortUrlConfig;

    public UrlMappingMapper(ShortUrlConfig config) {
        this.shortUrlConfig = config;
    }

    public UrlMapping toEntity(CreateShortUrlDTO createShortUrlDTO) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(createShortUrlDTO.getOriginalUrl());
        urlMapping.setAlias(createShortUrlDTO.getAlias());
        urlMapping.setExpirationDate(LocalDateTime.now()
                .plusMinutes(createShortUrlDTO.getExpirationMinutes() != null ?
                        createShortUrlDTO.getExpirationMinutes() :
                        shortUrlConfig.getStdExpirationMinutes()));
        return urlMapping;
    }

    public ResponseShortUrlDTO toResponseDTO(UrlMapping urlMapping) {
        ResponseShortUrlDTO dto = new ResponseShortUrlDTO();
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .build()
                        .toUriString();
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setShortUrl(baseUrl + "/" + urlMapping.getAlias());
        dto.setAlias(urlMapping.getAlias());
        dto.setExpirationDate(urlMapping.getExpirationDate());
        return dto;
    }
}
