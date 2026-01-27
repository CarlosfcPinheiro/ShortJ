package com.study.shortJ.service;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.exception.CustomAliasException;
import com.study.shortJ.mapper.UrlMappingMapper;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final UrlMappingMapper urlMappingMapper;

    public UrlMappingService(UrlMappingRepository urlMappingRepository, UrlMappingMapper urlMappingMapper) {
        this.urlMappingMapper = urlMappingMapper;
        this.urlMappingRepository = urlMappingRepository;
    }

    // Create a short URL mapping based on the provided DTO that contains original URL, custom alias, and expiration time.
    public UrlMapping createShortUrl(CreateShortUrlDTO createShortUrlDTO) {
        String alias = (createShortUrlDTO.getAlias() == null || createShortUrlDTO.getAlias().isEmpty())
                ? UUID.randomUUID().toString().substring(0, 8)
                : createShortUrlDTO.getAlias();
        if (urlMappingRepository.findByAlias(alias).isPresent()) throw new CustomAliasException("Custom alias already in use");
        createShortUrlDTO.setAlias(alias);
        UrlMapping urlMapping = urlMappingMapper.toEntity(createShortUrlDTO);
        return urlMappingRepository.save(urlMapping);
    }

    // Retrieve the original URL associated with the given alias, if it exists.
    public Optional<String> getOriginalUrl(String alias) {
        Optional<UrlMapping> mapping = urlMappingRepository.findByAlias(alias);
        if (mapping.isPresent() && mapping.get().getExpirationDate().isAfter(java.time.LocalDateTime.now())) {
            return Optional.of(mapping.get().getOriginalUrl());
        }
        return Optional.empty();
    }
}
