package com.dgtt.business.config;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.dgtt.admin.config;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurer;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.interceptor.CacheResolver;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
//import org.springframework.cache.interceptor.SimpleCacheResolver;
//import org.springframework.cache.interceptor.SimpleKeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import net.sf.ehcache.config.CacheConfiguration;
//import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
//import org.springframework.context.annotation.Lazy;
//
///**
// *
// * @author DELL
// */
//@Configuration
//@EnableCaching
//public class EhCacheConfig implements CachingConfigurer {
//
//    @Lazy
//    @Bean(destroyMethod = "shutdown")
//    public net.sf.ehcache.CacheManager ehCacheManager() {
//        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
//        config.addCache(cacheConfig("categoryCache", 1000, 60 * 10));
//        return net.sf.ehcache.CacheManager.newInstance(config);
//    }
//
//    private CacheConfiguration cacheConfig(String name, long maxEntries, long timeLive) {
//        CacheConfiguration config = new CacheConfiguration();
//        config.setName(name);
//        config.setMaxEntriesLocalHeap(maxEntries);
//        config.setMemoryStoreEvictionPolicyFromObject(MemoryStoreEvictionPolicy.LFU);
//        if (timeLive > 0) {
//            config.setTimeToLiveSeconds(timeLive);
//        }
//        return config;
//    }
//
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return new EhCacheCacheManager(ehCacheManager());
//    }
//
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return new SimpleKeyGenerator();
//    }
//
//    @Bean
//    @Override
//    public CacheResolver cacheResolver() {
//        return new SimpleCacheResolver(cacheManager());
//    }
//
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        return new SimpleCacheErrorHandler();
//    }
//
//}
