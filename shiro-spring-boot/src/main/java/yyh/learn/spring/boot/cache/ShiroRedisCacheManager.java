package yyh.learn.spring.boot.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroRedisCacheManager implements CacheManager {

    @Autowired
    ShiroRedisCache shiroRedisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return shiroRedisCache;
    }
}
