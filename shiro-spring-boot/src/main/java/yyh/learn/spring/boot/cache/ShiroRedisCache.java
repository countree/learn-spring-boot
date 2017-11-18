package yyh.learn.spring.boot.cache;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache<K, V> implements Cache<K, V> {

    @Autowired
    RedisTemplate<K, V> redisTemplate;
    @Autowired
    CacheManager redisCacheManager;

    @Override
    public V get(Object k) throws CacheException {
        org.springframework.cache.Cache cache = redisCacheManager.getCache(CacheKeyName.TEST_CACHE_NAME_ONE);
        cache.get(k);
//        cache.clear();
        return redisTemplate.opsForValue().get(k);
    }

    @Override
    public V put(final K k, final V v) throws CacheException {
        org.springframework.cache.Cache cache = redisCacheManager.getCache(CacheKeyName.TEST_CACHE_NAME_ONE);
        cache.put(k, v);
//        redisTemplate.opsForValue().set(k, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = redisTemplate.opsForValue().get(k);
        redisTemplate.delete(k);
        return v;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        // 数据库的大小
        return redisTemplate.execute(new RedisCallback<Integer>() {
            @Override
            public Integer doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.dbSize().intValue();
            }
        });
    }

    @Override
    public Set<K> keys() {
        // 数据库的大小
        return redisTemplate.keys((K) "*");
    }

    @Override
    public Collection<V> values() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.dbSize();
            }
        });
        return null;
    }
}
