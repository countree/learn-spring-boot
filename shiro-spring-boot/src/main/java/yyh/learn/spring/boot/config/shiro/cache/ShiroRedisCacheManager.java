package yyh.learn.spring.boot.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ShiroRedisCacheManager implements CacheManager {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);

    org.springframework.cache.CacheManager cacheManager;
    RedisTemplate redisTemplate;

    /**
     * 根据spring的统一配置获取缓存
     *
     * @param cacheManager
     * @param redisTemplate
     */
    public ShiroRedisCacheManager(org.springframework.cache.CacheManager cacheManager, RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
        Cache shiroCache = cacheMap.get(cacheName);
        return shiroCache == null ? new ShiroRedisCache<K, V>(cache, redisTemplate) : shiroCache;
    }
}
