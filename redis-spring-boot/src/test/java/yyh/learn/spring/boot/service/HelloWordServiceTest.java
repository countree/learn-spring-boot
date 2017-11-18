package yyh.learn.spring.boot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yyh.learn.spring.boot.RedisApplication;
import yyh.learn.spring.boot.cache.CacheNameConstant;
import yyh.learn.spring.boot.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RedisApplication.class)
public class HelloWordServiceTest {
    @Autowired
    HelloWordService helloWordService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    CacheManager cacheManager;

    @Test
    public void testCacheManager() throws Exception {
        System.out.println("所有的缓存名称:" + cacheManager.getCacheNames());
    }

    @Test
    public void testTemplae() {
        stringRedisTemplate.opsForValue().set("jjjjjjj", "hhahahahahhah");
    }

    @Test
    public void getHello() throws Exception {
        System.out.println(helloWordService.getUser("testEntity"));
        System.out.println(helloWordService.getHello());
        System.out.println(helloWordService.getKeyTest());

    }

    @Test
    public void testCacheNames() throws Exception {
        Cache sessionCache = cacheManager.getCache(CacheNameConstant.SESSION_CACHE_NAME);
        sessionCache.put("session", "session vaaaaaaaaa11111111111");
        System.out.println(redisTemplate.opsForZSet().getOperations().boundValueOps("session").getAndSet("vaaaaaaaaa111111111113333"));
        Cache authorizationCache = cacheManager.getCache(CacheNameConstant.AUTHORIZATION_CACHE_NAME);
        authorizationCache.put("authorization", "authorization vaaaaaaaaa22222222222");
        stringRedisTemplate.opsForValue().set("key-test-yhh", "sdlfkjsdlkfjl 伺机待发");
        redisTemplate.opsForValue().set("key-test-yyh", "第二次设置key-test-yyh");
        redisTemplate.opsForValue().set("key-test-entity", new User("entity value"));
    }

}
