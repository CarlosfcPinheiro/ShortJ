package com.study.shortJ.controller;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UrlController {

    private final UrlMappingService urlMappingService;

    public UrlController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping("/create")
    public ResponseEntity<UrlMapping> createUrl(@RequestBody CreateShortUrlDTO createShortUrlDTO) {
        UrlMapping urlMapping = urlMappingService.createShortUrl(createShortUrlDTO);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/{alias}")
    public String redirect(@PathVariable String alias){
        Optional<String> originalUrl = urlMappingService.getOriginalUrl(alias);
        return originalUrl.map(url -> "redirect:" + url).orElse("redirect:/");
    }
}
