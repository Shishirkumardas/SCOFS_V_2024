package com.example.scofs.Services;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheableService {

    public final CacheManager cacheManager;


    public CacheableService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictSingleCacheValue(String cacheName, String cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(cacheKey);
        }
    }

    public void evictAllCacheValues(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * Create a Cache Eviction to clear all the <br>
     * cache.
     *
     * @author Christian 17 Apr 2020
     */
    public void clearAllCache() {

        // Iterate all the cache names available in the cacheManager.
        for (String cacheName : cacheManager.getCacheNames()) {

            // Call functions for cleaning the cache by cache name.
            evictAllCacheValues(cacheName);
        }
    }

    /**
     * Cache service method use for returning all {@link Cache} <br>
     * that is register in the {@link CacheManager}.
     *
     * @return List<Cache>
     * @author Christian 18 Apr 2020
     */
    public List<Cache> listAllCacheNames() {
        List<Cache> cacheList = new ArrayList<>();
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheList.add(cacheManager.getCache(cacheName));
        }
        return cacheList;
    }


    /**
     * Cache service method use for clearing the {@link Cache} <br>
     * using the clazzName of the object. Example of cacheKey: <br>
     * <p>
     * <blockquote>
     * <pre>
     * <br>
     *      "PortfolioV2Service.getAll"
     * </pre>
     * </blockquote>
     * <br>
     * <tt>PortfolioV2Service</tt> - the clazz name of the object. <br>
     * <tt>getAll</tt> - the clazz method of the object. <br>
     *
     * @param className the class name of the object.
     */
    public void evictCachedByClassName(Class<?> className) {

        // Get the class name.
        // Fix class when it is adding of cache wildcard.
        // Erica 22 Feb 2022
        String clazzName = className.getSimpleName();
        String serviceName = clazzName.contains("$")
                ? clazzName.substring(0, clazzName.indexOf("$"))
                : clazzName;
        evictCachedByClassName(serviceName);
    }

    /**
     * Cache service method use for clearing the {@link Cache} <br>
     */
    public void evictCachedByClassName(String className) {

        // Iterate available form the cache manager
        for (String cacheName : cacheManager.getCacheNames()) {

            // Get the token of the cacheName. Separate by dot(.)
            // [0] - className of the object or service. E.g. PortfolioService
            // [1] - method name of the object or service. E.g. getAll
            String[] tokenizer = cacheName.split(". ");

            // Check if the cacheName contains the className in the string.
            boolean isContain = tokenizer.length > 0 && tokenizer[0].contains(className);

            // If isContain return true. It means the className exist in
            // cache manager so clear the cache with the use of cacheName.
            if (isContain) {
                evictAllCacheValues(cacheName);
            }
        }
    }

    public Cache getCacheByName(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
}
