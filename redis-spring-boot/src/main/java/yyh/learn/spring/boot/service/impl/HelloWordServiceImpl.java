package yyh.learn.spring.boot.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yyh.learn.spring.boot.service.HelloWordService;

@Service
public class HelloWordServiceImpl implements HelloWordService {

    @Cacheable(value = "hahaCacheName")
    @Override
    public String getHello() {
        System.out.println("请求来了 ，走缓存了没呢？？？");
        return "hello redis !!!";
    }
}
