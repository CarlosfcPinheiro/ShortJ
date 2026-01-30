package com.study.shortJ.controller;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.service.UrlMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrlController {

    private final UrlMappingService urlMappingService;

    public UrlController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseShortUrlDTO> createUrl(@RequestBody CreateShortUrlDTO createShortUrlDTO) {
        ResponseShortUrlDTO urlMapping = urlMappingService.createShortUrl(createShortUrlDTO);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias){
        String originalUrl = urlMappingService.getOriginalUrl(alias);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }
}
