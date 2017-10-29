package yyh.learn.spring.boot.entity;

import java.io.Serializable;

public class User implements Serializable{
    private String name;
    private String password;
    private Integer age;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
