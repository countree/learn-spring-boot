package yyh.learn.spring.boot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 可以通过service 注解
 * 也可以在Config配置类里用 @Bean加载
 *
 * @param <K>
 * @param <V>
 */
@Service
public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    @Autowired
    RedisTemplate<K, V> redisTemplate;

    //======================== 自定义缓存使用=====================

    @Override
    public void set(K var1, V var2) {
        redisTemplate.opsForValue().set(var1, var2);
    }

    @Override
    public void set(K var1, V var2, long var3, TimeUnit var5) {
        redisTemplate.opsForValue().set(var1, var2, var3, var5);
    }

    @Override
    public void set(K var1, V var2, long expireTime) {
        redisTemplate.opsForValue().set(var1, var2);
    }

    @Override
    public void multiSet(Map<? extends K, ? extends V> var1) {
        redisTemplate.opsForValue().multiSet(var1);
    }

    @Override
    public V getAndSet(K var1, V var2) {
        return redisTemplate.opsForValue().getAndSet(var1, var2);
    }

    @Override
    public V get(Object k) {
        return redisTemplate.opsForValue().get(k);
    }
}
