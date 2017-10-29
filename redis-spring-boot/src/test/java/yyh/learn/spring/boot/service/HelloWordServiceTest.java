package yyh.learn.spring.boot.service;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yyh.learn.spring.boot.RedisApplication;
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
    CacheManager redisCacheManager;

    @Test
    public void testCacheManager() throws Exception {
//        Cache cache = redisCacheManager.getCache("");
        System.out.println("cache name ======"+redisCacheManager.getCacheNames());
    }

    @Test
    public void getHello() throws Exception {


        stringRedisTemplate.opsForValue().set("key-test-yhh", "sdlfkjsdlkfjl 山东师范骄傲了");
        stringRedisTemplate.opsForValue().set("key-test-yhh", "sdlfkjsdlkfjl 竟然没换1");
        redisTemplate.opsForValue().set("key-tst-yyh", "竟然换了 说什么不是一样的东西");
        redisTemplate.opsForValue().set("key-test-entity", new User("entity value"));
        System.out.println(helloWordService.getHello());
        System.out.println(helloWordService.getKeyTest());

    }

}
