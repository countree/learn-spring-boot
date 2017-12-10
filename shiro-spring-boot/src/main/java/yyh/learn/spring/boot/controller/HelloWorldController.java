package yyh.learn.spring.boot.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yyh.learn.spring.boot.cache.CacheNameConstant;
import yyh.learn.spring.boot.config.shiro.CustomAuthRealm;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String hello() {
        return "Hello,World!";
    }

    @Autowired
    CacheManager redisCacheManager;
    @Autowired
    CustomAuthRealm customAuthRealm;

    @RequestMapping("/login")
    public String login(String name, String pwd) {
        if (StringUtils.isBlank(pwd))
            pwd = "yyh-pwd";
        if (StringUtils.isBlank(name))
            name = "yyh-default";
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(name);
        token.setPassword(pwd.toCharArray());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return "login successful!";
    }


    @RequestMapping("/put")
    public Object put(String key) {
        String value = "kkkkkkkkkkkkkkkkkkkkkkk";
        return redisCacheManager.getCache(CacheNameConstant.SHIRO_AUTHORIZATION_CACHE_NAME).put(key, value);
    }

    @RequestMapping("/get")
    public Object login3() {
        return customAuthRealm.getCacheAuthorizationInfo();
    }

    @RequestMapping("/clear")
    public String login2() {
        redisCacheManager.getCache(CacheNameConstant.SHIRO_AUTHORIZATION_CACHE_NAME).clear();
        return "all clear";
    }

    @RequestMapping("/remove")
    public String login6(String key) {
        customAuthRealm.removeCacheAuthorizationInfo();
        return "remove value";
    }


    @RequestMapping("/authz")
    public String logina() {
        return "test authorization,should be redirect to 403";
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
