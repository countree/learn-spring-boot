package yyh.learn.spring.boot.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yyh.learn.spring.boot.cache.CacheNameConstant;
import yyh.learn.spring.boot.entity.User;
import yyh.learn.spring.boot.service.HelloWordService;

/**
 * @CacheConfig(cacheNames="test") 使用在类上，每个方法公用这个cache
 */
@Service
@CacheConfig(cacheNames = CacheNameConstant.DEFAULT_CACHE_NAME)
public class HelloWordServiceImpl implements HelloWordService {
    /**
     * @Cacheable :主要针对方法配置，能够根据方法的请求参数对其结果进行缓存 #：SpEL表达式
     * 至少指定一个缓存的名称（value），多个:(value={"cache1","cache2"})
     * 若缓存的key为空则缺省按照方法的所有参数进行组合或自定义的keyGenerator，自定义key：key="#userName.concat(#password)"
     * condition:缓存条件，可为空  condition="#userName.length()>4"
     */
    @Cacheable
    @Override
    public String getHello() {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        return "hello redis !!!";
    }

    @Cacheable(key = "#name")
    @Override
    public User getUser(String name) {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        User user = new User();
        user.setName(name);
        user.setAge(200);
        return user;
    }

    @Cacheable(key = "'keyCustom'")
    @Override
    public String getKeyTest() {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        return "hello redis key test!!!";
    }
}
