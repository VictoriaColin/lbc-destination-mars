package com.kenzie.appserver.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kenzie.appserver.service.model.Flight;

import java.util.concurrent.TimeUnit;

public class CacheStore {
    private Cache<String, Flight> cache;

    public CacheStore(int expiry, TimeUnit timeUnit) {
        // initialize the cache
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiry, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }
    public Flight get(String key) {
        // Write your code here
        // Retrieve and return the concert
        return cache.getIfPresent(key);
    }

    public void evict(String key) {
        // Write your code here
        // Invalidate/evict the concert from cache
        if(key != null){
            cache.invalidate(key);
        }
    }

    public void add(String key, Flight value) {
        // Write your code here
        // Add concert to cache
        cache.put(key,value);
    }
}
