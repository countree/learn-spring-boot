package yyh.learn.spring.boot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
