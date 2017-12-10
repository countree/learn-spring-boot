package yyh.learn.spring.boot.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final Long serialVersionUID = -11145555545445L;


    private String name;
    private String password;
    private Integer age;

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
