package com.study.shortJ.service;

import com.study.shortJ.exception.custom.ResourceNotFoundException;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiUrlService {

    private final UrlMappingRepository urlMappingRepository;

    public ApiUrlService(UrlMappingRepository urlMappingRepository) {
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
}
