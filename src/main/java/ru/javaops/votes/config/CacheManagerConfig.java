package ru.javaops.votes.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CacheManagerConfig {

    @Bean("restaurantCacheManager")
    public CacheManager restaurantCacheManager() {
        var cacheManager = new CaffeineCacheManager("restaurant");
        cacheManager.setCaffeine(restaurantCacheBuilder());
        return cacheManager;
    }

    public Caffeine<Object, Object> restaurantCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(5000)
                .expireAfterAccess(Duration.ofSeconds(300));
    }

    @Bean("defaultCacheManager")
    @Primary
    public CacheManager defaultCacheManager() {
        var cacheManager = new CaffeineCacheManager("users");
        cacheManager.setCaffeine(defaultCacheBuilder());
        return cacheManager;
    }

    public Caffeine<Object, Object> defaultCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(5000)
                .expireAfterAccess(Duration.ofSeconds(60));
    }

    @Bean("allRestaurantsCacheManager")
    public CacheManager allRestaurantsCacheManager() {
        var cacheManager = new CaffeineCacheManager("all_restaurants");
        cacheManager.setCaffeine(defaultCacheBuilder());
        return cacheManager;
    }
}
