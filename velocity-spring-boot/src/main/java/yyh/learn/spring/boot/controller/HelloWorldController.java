package yyh.learn.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
    @RequestMapping("/")
    public String index(String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping("/hello")
    public String hello(String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
