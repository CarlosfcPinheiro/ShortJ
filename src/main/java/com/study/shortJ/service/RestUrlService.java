package com.study.shortJ.service;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.exception.CustomAliasException;
import com.study.shortJ.exception.ResourceNotFoundException;
import com.study.shortJ.mapper.UrlMappingMapper;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import com.study.shortJ.utils.UrlShortenerHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RestUrlService {

    private final UrlMappingRepository urlMappingRepository;
    private final UrlMappingMapper urlMappingMapper;
    private final UrlShortenerHelper urlShortenerHelper;

    private final RedisService redisService;

    public RestUrlService(
            UrlMappingRepository urlMappingRepository,
            UrlMappingMapper urlMappingMapper,
            UrlShortenerHelper urlShortenerHelper,
            RedisService redisService) {
        this.urlMappingMapper = urlMappingMapper;
        this.urlShortenerHelper = urlShortenerHelper;
        this.urlMappingRepository = urlMappingRepository;
        this.redisService = redisService;
    }

    public List<UrlMapping> getAllUrlMappings() {
        return urlMappingRepository.findAll();
    }

    public ResponseShortUrlDTO getUrlMappingByAlias(String alias) {
        String cachedUrl = redisService.getValue(alias);
        if (cachedUrl != null) {
            UrlMapping cachedMapping = new UrlMapping();
            cachedMapping.setAlias(alias);
            cachedMapping.setOriginalUrl(cachedUrl);
            return urlMappingMapper.toResponseDTO(cachedMapping);
        }
        UrlMapping urlMapping = getUrlMappingByAliasOnDatabase(alias);
        long secondsUntilExpiration = ChronoUnit.SECONDS.between(
                LocalDateTime.now(),
                urlMapping.getExpirationDate()
        );
        redisService.setValueWithExpiration(alias, urlMapping.getOriginalUrl(), secondsUntilExpiration);
        return urlMappingMapper.toResponseDTO(urlMapping);
    }

    public void deleteUrlMappingByAlias(String alias){
        UrlMapping urlMapping = getUrlMappingByAliasOnDatabase(alias);
        urlMappingRepository.delete(urlMapping);
    }

    public ResponseShortUrlDTO createShortUrl(CreateShortUrlDTO createShortUrlDTO) {
        // Determine if a custom alias is provided and validate it, otherwise generate a new alias
        String alias = determineAlias(createShortUrlDTO);
        createShortUrlDTO.setAlias(alias);

        UrlMapping urlMapping = urlMappingMapper.toEntity(createShortUrlDTO);
        UrlMapping savedMapping = urlMappingRepository.save(urlMapping);

        redisService.setValueWithExpiration(alias, createShortUrlDTO.getOriginalUrl(), createShortUrlDTO.getExpirationMinutes()*60);
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

    private UrlMapping getUrlMappingByAliasOnDatabase(String alias) {
        return urlMappingRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResourceNotFoundException("No short url found for alias: " + alias));
    }
}
