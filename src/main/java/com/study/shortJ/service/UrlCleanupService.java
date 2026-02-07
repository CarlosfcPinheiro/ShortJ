package com.study.shortJ.service;

import com.study.shortJ.repository.UrlMappingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlCleanupService {

    private final UrlMappingRepository urlMappingRepository;

    public UrlCleanupService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredUrlMappings() {
        urlMappingRepository.deleteByExpirationDateBefore(LocalDateTime.now());
    }
}
