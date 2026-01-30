package com.study.shortJ.utils;

import com.study.shortJ.config.ShortUrlConfig;
import org.hashids.Hashids;
import org.springframework.stereotype.Component;

@Component
public class UrlShortenerHelper {

    private final ShortUrlConfig shortUrlConfig;

    public UrlShortenerHelper(ShortUrlConfig shortUrlConfig) {
        this.shortUrlConfig = shortUrlConfig;
    }

    public String generateAlias(Long urlMappingId){
        Hashids hashids = new Hashids(
                shortUrlConfig.getHashSaltSecret(),
                shortUrlConfig.getKeyLength(),
                shortUrlConfig.getAllowedChars());
        return hashids.encode(urlMappingId);
    }
}
