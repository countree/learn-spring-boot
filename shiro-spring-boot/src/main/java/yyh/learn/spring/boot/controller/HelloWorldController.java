package yyh.learn.spring.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String hello() {
        return "Hello,World!";
    }

    @RequestMapping("/login")
    public String login() {
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername("yyh");
        token.setPassword("yyy".toCharArray());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "login successful!";
    }

    @RequestMapping("/hello")
    public String login3() {
        return "hello!";
    }

    @RequestMapping("/haha")
    public String login2() {
        return "haha!";
    }

    @RequestMapping("/hehe")
    public String login6() {
        return "hehe!";
    }

    @RequestMapping("/logout")
    public String login7() {
        return "logout";
    }

    @RequestMapping("/403")
    public String login1() {
        return "403,没有权限访问";
    }
}
