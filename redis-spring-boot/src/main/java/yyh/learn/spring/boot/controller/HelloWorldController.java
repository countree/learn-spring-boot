package yyh.learn.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yyh.learn.spring.boot.service.HelloWordService;

@RestController
public class HelloWorldController {

    @Autowired
    HelloWordService helloWordService;

    @RequestMapping("/")
    public String hello() {
        return helloWordService.getHello();
    }
}
