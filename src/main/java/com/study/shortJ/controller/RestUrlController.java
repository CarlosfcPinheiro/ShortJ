package com.study.shortJ.controller;

import com.study.shortJ.dto.CreateShortUrlDTO;
import com.study.shortJ.dto.ResponseShortUrlDTO;
import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.service.RestUrlService;
import com.study.shortJ.exception.model.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/urls")
public class RestUrlController {

    private final RestUrlService restUrlService;

    public RestUrlController(RestUrlService restUrlService) {
        this.restUrlService = restUrlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ApiResponse> createUrl(@RequestBody @Valid CreateShortUrlDTO createShortUrlDTO) {
        ResponseShortUrlDTO urlMapping = restUrlService.createShortUrl(createShortUrlDTO);
        ApiResponse response = new ApiResponse("Short URL created successfully", true, 201, urlMapping);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUrls() {
        List<UrlMapping> urlMappings = restUrlService.getAllUrlMappings();
        if (urlMappings.isEmpty()) {
            ApiResponse response = new ApiResponse("No URL mappings found", false, 404, null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse response = new ApiResponse("Fetched all URL mappings successfully", true, 200, urlMappings);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<ApiResponse> getUrlByAlias(@PathVariable String alias){
        UrlMapping url = restUrlService.getUrlMappingByAlias(alias);
        ApiResponse response = new ApiResponse("Fetched URL by alias successfully", true, 200, url);
        return ResponseEntity.status(200).body(response);
    }

    // inaccessible to general users, only for admin use
    @DeleteMapping("/{alias}")
    public ResponseEntity<Void> deleteUrlByAlias(@PathVariable String alias) {
        restUrlService.deleteUrlMappingByAlias(alias);
        return ResponseEntity.noContent().build();
    }
}
