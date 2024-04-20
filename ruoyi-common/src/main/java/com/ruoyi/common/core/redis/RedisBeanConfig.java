package com.ruoyi.common.core.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisBeanConfig {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisCacheImpl redisCache(RedisTemplate<?, ?> redisTemplate) {
        final RedisCacheImpl redisCache = new RedisCacheImpl();
        redisCache.setRedisTemplate(redisTemplate);
        return redisCache;
    }

    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public NoRedisCache noRedisCache() {
        return new NoRedisCache();
    }

}
