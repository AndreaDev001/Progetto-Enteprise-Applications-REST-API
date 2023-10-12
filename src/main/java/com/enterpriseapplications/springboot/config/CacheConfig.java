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
    public static final String CACHE_ALL_PRODUCTS = "ALL_PRODUCTS";
    public static final String CACHE_ALL_USERS = "ALL_USERS";
    public static final String CACHE_ALL_REPORTS = "ALL_REPORTS";
    public static final String CACHE_ALL_BANS = "ALL_PRODUCTS";


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_SEARCH_PRODUCTS,CACHE_SEARCH_USERS,CACHE_SEARCH_REPORTS,CACHE_SEARCH_BANS);
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_PRODUCTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
    public void productSearchCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_PRODUCTS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_USERS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void userSearchCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_USERS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_REPORTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void reportsSearchCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_REPORTS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_SEARCH_BANS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void banSearchCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_SEARCH_BANS,dateTimeFormatter.format(LocalDateTime.now())));
    }

    @CacheEvict(allEntries = true,value = {CACHE_ALL_PRODUCTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void allReportsCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_ALL_REPORTS,dateTimeFormatter.format(LocalDateTime.now())));
    }
    @CacheEvict(allEntries = true,value = {CACHE_ALL_PRODUCTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void allBansCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_ALL_BANS,dateTimeFormatter.format(LocalDateTime.now())));
    }
    @CacheEvict(allEntries = true,value = {CACHE_ALL_PRODUCTS})
    @Scheduled(fixedDelay = 5 * 60 * 1000,initialDelay = 1000)
    public void allUsersCacheEvict() {
        log.info(String.format("Cache [%s] has been flushed at [%s]",CACHE_ALL_USERS,dateTimeFormatter.format(LocalDateTime.now())));
    }
}
