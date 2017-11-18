package yyh.learn.spring.boot.service;

import yyh.learn.spring.boot.entity.User;

public interface HelloWordService {
    String getHello();

    User getUser(String name);

    String getKeyTest();
}
