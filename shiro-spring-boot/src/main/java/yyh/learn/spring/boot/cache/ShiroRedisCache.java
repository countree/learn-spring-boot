package yyh.learn.spring.boot.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private org.springframework.cache.Cache cache;

    RedisTemplate<K, V> redisTemplate;

    public ShiroRedisCache() {
    }

    public ShiroRedisCache(org.springframework.cache.Cache cache, RedisTemplate redisTemplate) {
        this.cache = cache;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(Object k) throws CacheException {
        return (V) cache.get(k);
    }

    @Override
    public V put(final K k, final V v) throws CacheException {
        cache.put(k, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        cache.evict(k);
        return get(k);
    }

    @Override
    public void clear() throws CacheException {
        cache.clear();
    }

    @Override
    public int size() {
        //zset的key有多少个
//        return redisTemplate.keys((K) cache.getName()).size();
        return redisTemplate.opsForZSet().size((K) cache.getName()).intValue();
    }

    @Override
    public Set<K> keys() {
        //所有的keys
        return redisTemplate.keys((K) cache.getName());
    }

    @Override
    public Collection<V> values() {
        return redisTemplate.boundZSetOps((K) cache.getName()).range(0, redisTemplate.opsForZSet().size((K) cache.getName()));
    }
}
