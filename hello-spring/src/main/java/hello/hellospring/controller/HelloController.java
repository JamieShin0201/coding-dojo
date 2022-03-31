package hello.hellospring.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "data") String data,  Model model) {
        model.addAttribute("data", data);
        return "index";
    }

    @ResponseBody
    @GetMapping("/hello-string")
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @ResponseBody
    @GetMapping("/hello-api")
    public Hello helloApi(@RequestParam("name") String name) {
        return new Hello(name);
    }

    @Getter
    @AllArgsConstructor
    static class Hello {
        String name;
    }
}
