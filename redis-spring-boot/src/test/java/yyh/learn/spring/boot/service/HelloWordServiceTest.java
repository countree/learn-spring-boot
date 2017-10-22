package yyh.learn.spring.boot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import yyh.learn.spring.boot.RedisApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RedisApplication.class)
public class HelloWordServiceTest {
    @Autowired
    HelloWordService helloWordService;

    @Test
    public void getHello() throws Exception {
        System.out.println(helloWordService.getHello());

    }

}
