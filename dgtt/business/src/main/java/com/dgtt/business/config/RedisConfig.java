package com.dgtt.business.config;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.dgtt.admin.config;
//
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// *
// * @author sadfsafbhsaid
// */
//@Configuration
//@EnableCaching
//public class RedisConfig {

    //    @Bean
//    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        // default 1
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(this.timeToLive)
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
//                .disableCachingNullValues();
//        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(config)
//                .transactionAware()
//                .build();
//        return redisCacheManager;
//    }
//    @Override
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(createConcurrentMapCache("plance", 1000, 5), createConcurrentMapCache("interference", 1000, 5)));
//        return cacheManager;
//    }
//    public Cache createConcurrentMapCache(final String name,long maximumSize,long minuteTimeLive) {
//        return new ConcurrentMapCache(name,
//                CacheBuilder.newBuilder().expireAfterWrite(minuteTimeLive, TimeUnit.MINUTES).maximumSize(maximumSize).build().asMap(), false);
//    }
    
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(keySerializer());
//        redisTemplate.setHashKeySerializer(keySerializer());
//        redisTemplate.setValueSerializer(valueSerializer());
//        redisTemplate.setHashValueSerializer(valueSerializer());
//        return redisTemplate;
//    }
//
//    private RedisSerializer<String> keySerializer() {
//        return new StringRedisSerializer();
//    }
//
//    private RedisSerializer<Object> valueSerializer() {
//        return new GenericJackson2JsonRedisSerializer();
//    }
//}
