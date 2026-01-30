package com.study.shortJ.controller;

import com.study.shortJ.model.UrlMapping;
import com.study.shortJ.service.ApiUrlService;
import com.study.shortJ.exception.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class ApiUrlController {

    private final ApiUrlService apiUrlService;

    public ApiUrlController(ApiUrlService apiUrlService) {
        this.apiUrlService = apiUrlService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUrls() {
        List<UrlMapping> urlMappings = apiUrlService.getAllUrlMappings();
        if (urlMappings.isEmpty()) {
            ApiResponse response = new ApiResponse("No URL mappings found", false, 404, null);
            return ResponseEntity.status(404).body(response);
        }
        ApiResponse response = new ApiResponse("Fetched all URL mappings successfully", true, 200, urlMappings);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<ApiResponse> getUrlByAlias(@PathVariable String alias){
        UrlMapping url = apiUrlService.getUrlMappingByAlias(alias);
        ApiResponse response = new ApiResponse("Fetched URL by alias successfully", true, 200, url);
        return ResponseEntity.status(200).body(response);
    }

    // inaccessible to general users, only for admin use
    @DeleteMapping("/{alias}")
    public ResponseEntity<Void> deleteUrlByAlias(@PathVariable String alias) {
        apiUrlService.deleteUrlMappingByAlias(alias);
        return ResponseEntity.noContent().build();
    }
}
