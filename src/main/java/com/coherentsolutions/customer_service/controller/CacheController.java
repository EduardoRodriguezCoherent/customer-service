package com.coherentsolutions.customer_service.controller;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private static final String CUSTOMERS_CACHE = "customersCache";
    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("/stats")
    public Map<String, Long> getCacheStats() {
        org.springframework.cache.Cache cache = Objects.requireNonNull(cacheManager.getCache(CUSTOMERS_CACHE));

        Object nativeCache = cache.getNativeCache();

        CacheStats stats = ((Cache<?, ?>) nativeCache).stats();

        return Map.of(
                "hitCount", stats.hitCount(),
                "missCount", stats.missCount(),
                "loadSuccessCount", stats.loadSuccessCount(),
                "loadFailureCount", stats.loadFailureCount(),
                "totalLoadTime", stats.totalLoadTime(),
                "evictionCount", stats.evictionCount(),
                "evictionWeight", stats.evictionWeight()
        );
    }
}
