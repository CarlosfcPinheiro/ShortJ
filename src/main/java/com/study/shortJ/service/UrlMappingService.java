package com.study.shortJ.service;

import com.study.shortJ.exception.custom.UrlMappingNotFoundOrExpiredException;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    // Retrieve the original URL associated with the given alias, if it exists.
    public String getOriginalUrl(String alias) {
        UrlMapping mapping = getUrlMappingByAlias(alias);
        return mapping.getOriginalUrl();
    }

    //TODO get only not expired urls
    private UrlMapping getUrlMappingByAlias(String alias) {
        return urlMappingRepository
                .findByAlias(alias)
                .orElseThrow(() -> new UrlMappingNotFoundOrExpiredException("No short url found for alias: " + alias));
    }
}
