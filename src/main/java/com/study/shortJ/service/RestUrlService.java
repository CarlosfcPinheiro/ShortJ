package com.study.shortJ.service;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.exception.custom.CustomAliasException;
import com.study.shortJ.exception.custom.ResourceNotFoundException;
import com.study.shortJ.mapper.UrlMappingMapper;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import com.study.shortJ.utils.UrlShortenerHelper;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestUrlService {

    private final UrlMappingRepository urlMappingRepository;
    private final UrlMappingMapper urlMappingMapper;
    private final UrlShortenerHelper urlShortenerHelper;

    public RestUrlService(UrlMappingRepository urlMappingRepository,
                          UrlMappingMapper urlMappingMapper,
                          UrlShortenerHelper urlShortenerHelper) {
        this.urlMappingMapper = urlMappingMapper;
        this.urlShortenerHelper = urlShortenerHelper;
        this.urlMappingRepository = urlMappingRepository;
    }

    public List<UrlMapping> getAllUrlMappings() {
        return urlMappingRepository.findAll();
    }

    public UrlMapping getUrlMappingByAlias(String alias) {
        return urlMappingRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResourceNotFoundException("No short url found for alias: " + alias));
    }

    public void deleteUrlMappingByAlias(String alias){
        UrlMapping urlMapping = getUrlMappingByAlias(alias);
        urlMappingRepository.delete(urlMapping);
    }

    public ResponseShortUrlDTO createShortUrl(CreateShortUrlDTO createShortUrlDTO) {
        String alias = determineAlias(createShortUrlDTO);
        createShortUrlDTO.setAlias(alias);

        UrlMapping urlMapping = urlMappingMapper.toEntity(createShortUrlDTO);
        UrlMapping savedMapping = urlMappingRepository.save(urlMapping);

        return urlMappingMapper.toResponseDTO(savedMapping);
    }

    /*
    * Private Helper Methods
    * */
    private String determineAlias(CreateShortUrlDTO createShortUrlDTO) {
        String customAlias = createShortUrlDTO.getAlias();

        if (customAlias != null && !customAlias.isEmpty()) {
            validateCustomAlias(customAlias);
            return customAlias;
        }

        return generateAlias();
    }

    private void validateCustomAlias(String customAlias) {
        if (urlMappingRepository.findByAlias(customAlias).isPresent()) {
            throw new CustomAliasException("Custom alias already in use");
        }
    }

    private String generateAlias() {
        // Get the next sequential ID from the repository
        Long nextId = urlMappingRepository.getNextSequentialId();
        return urlShortenerHelper.generateAlias(nextId);
    }
}
