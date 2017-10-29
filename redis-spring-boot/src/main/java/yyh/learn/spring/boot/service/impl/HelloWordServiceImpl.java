package yyh.learn.spring.boot.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yyh.learn.spring.boot.service.HelloWordService;

@Service
@Cacheable("helloWordCacheName")
public class HelloWordServiceImpl implements HelloWordService {

    @Cacheable(key = "hello")
    @Override
    public String getHello() {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        return "hello redis !!!";
    }

    @Cacheable(key = "keytest")
    @Override
    public String getKeyTest() {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        return "hello redis key test!!!";
    }
}
