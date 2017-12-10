package yyh.learn.spring.boot.config.redis;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class CustomRedisCacheManager extends RedisCacheManager {

    private Map<String, RedisTemplate> redisTemplates;

    public CustomRedisCacheManager(RedisOperations redisOperations) {
        super(redisOperations);
    }

    /**
     * 不同的cache name使用不同的序列化
     *
     * @param redisOperations 默认的
     * @param map             自定义cache name 对应的序列化
     */
    public CustomRedisCacheManager(RedisOperations redisOperations, Map<String, RedisTemplate> map) {
        super(redisOperations);
        this.redisTemplates = map;
    }

    @Override
    protected RedisCache createCache(String cacheName) {
        long expiration = this.computeExpiration(cacheName);
        if (redisTemplates.get(cacheName) == null) {
            return new RedisCache(cacheName, null, getRedisOperations(), expiration);
        } else {
            return new RedisCache(cacheName, null, redisTemplates.get(cacheName), expiration);
        }
    }
}
