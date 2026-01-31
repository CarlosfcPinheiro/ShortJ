package com.study.shortJ.controller;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.exception.model.ApiResponse;

import com.study.shortJ.service.RestUrlService;
import com.study.shortJ.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrlController {

    private final UrlMappingService urlMappingService;
    private final RestUrlService restUrlService;

    public UrlController(UrlMappingService urlMappingService, RestUrlService restUrlService) {
        this.urlMappingService = urlMappingService;
        this.restUrlService = restUrlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ApiResponse> createUrl(@RequestBody CreateShortUrlDTO createShortUrlDTO) {
        ResponseShortUrlDTO urlMapping = restUrlService.createShortUrl(createShortUrlDTO);
        ApiResponse response = new ApiResponse("Short URL created successfully", true, 201, urlMapping);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias){
        String originalUrl = urlMappingService.getOriginalUrl(alias);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }
}
