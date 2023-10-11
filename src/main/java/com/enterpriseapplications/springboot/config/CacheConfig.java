package com.enterpriseapplications.springboot.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheConfig
{
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss");
    public static final String CACHE_SEARCH_PRODUCTS = "SEARCH_PRODUCTS";
    public static final String CACHE_SEARCH_USERS = "SEARCH_USERS";
    public static final String CACHE_SEARCH_REPORTS = "SEARCH_REPORTS";
    public static final String CACHE_SEARCH_BANS = "SEARCH_BANS";


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_SEARCH_PRODUCTS,CACHE_SEARCH_USERS,CACHE_SEARCH_REPORTS,CACHE_SEARCH_BANS);
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_PRODUCTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
    public void productCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_PRODUCTS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_USERS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void userCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_USERS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_REPORTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void reportsCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_REPORTS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_BANS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void banCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_BANS,dateTimeFormatter.format(LocalDateTime.now())));
    }
}
