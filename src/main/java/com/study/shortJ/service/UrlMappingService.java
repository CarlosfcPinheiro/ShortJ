package com.study.shortJ.service;

import com.study.shortJ.exception.UrlMappingNotFoundOrExpiredException;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final RedisService redisService;

    public UrlMappingService(
            UrlMappingRepository urlMappingRepository,
            RedisService redisService) {
        this.urlMappingRepository = urlMappingRepository;
        this.redisService = redisService;
    }

    // Retrieve the original URL associated with the given alias, if it exists.
    public String getOriginalUrl(String alias) {
        // First, check if the original URL is available in Redis cache
        String cachedUrl = redisService.getValue(alias);
        if (cachedUrl != null)
            return cachedUrl;

        UrlMapping mapping = getUrlMappingByAlias(alias);
        return mapping.getOriginalUrl();
    }

    /*
     // Private helper methods
    */
    private UrlMapping getUrlMappingByAlias(String alias) {
        return urlMappingRepository
                .findByAlias(alias)
                .orElseThrow(() -> new UrlMappingNotFoundOrExpiredException("No short url found for alias: " + alias));
    }
}
