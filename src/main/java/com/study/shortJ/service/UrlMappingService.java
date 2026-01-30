package com.study.shortJ.service;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.exception.custom.CustomAliasException;
import com.study.shortJ.mapper.UrlMappingMapper;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import com.study.shortJ.utils.UrlShortenerHelper;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final UrlMappingMapper urlMappingMapper;
    private final UrlShortenerHelper urlShortenerHelper;

    public UrlMappingService(UrlMappingRepository urlMappingRepository, UrlMappingMapper urlMappingMapper, UrlShortenerHelper urlShortenerHelper) {
        this.urlMappingMapper = urlMappingMapper;
        this.urlMappingRepository = urlMappingRepository;
        this.urlShortenerHelper = urlShortenerHelper;
    }

    // Create a short URL mapping based on the provided DTO that contains original URL, custom alias, and expiration time.
    public ResponseShortUrlDTO createShortUrl(CreateShortUrlDTO createShortUrlDTO) {
        String customAlias = createShortUrlDTO.getAlias();
        if (customAlias != null && !customAlias.isEmpty()) {
            if (urlMappingRepository.findByAlias(customAlias).isPresent()) {
                throw new CustomAliasException("Custom alias already in use");
            }
            createShortUrlDTO.setAlias(customAlias);
            UrlMapping urlMapping = urlMappingMapper.toEntity(createShortUrlDTO);
            return urlMappingMapper.toResponseDTO(urlMappingRepository.save(urlMapping));
        }
        createShortUrlDTO.setAlias(null);
        UrlMapping urlMapping = urlMappingMapper.toEntity(createShortUrlDTO);
        UrlMapping savedMapping = urlMappingRepository.save(urlMapping);

        String generatedAlias = urlShortenerHelper.generateAlias(savedMapping.getId());
        savedMapping.setAlias(generatedAlias);
        return urlMappingMapper.toResponseDTO(urlMappingRepository.save(savedMapping));
    }

    // Retrieve the original URL associated with the given alias, if it exists.
    public String getOriginalUrl(String alias) {
        UrlMapping mapping = getUrlMappingByAlias(alias);
        return mapping.getOriginalUrl();
    }

    //TODO get only not expired urls (can be resolved using redis to store shorted urls with expiration)
    private UrlMapping getUrlMappingByAlias(String alias) {
        return urlMappingRepository
                .findByAlias(alias)
                .orElseThrow(() -> new CustomAliasException("No short url found for alias: " + alias));
    }
}
