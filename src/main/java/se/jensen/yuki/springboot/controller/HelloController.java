package se.jensen.yuki.springboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String hello() {
        return "Hello from Spring Boot";
    }

    @PostMapping
    public String post(@RequestBody String message) {
        return message + " received";
    }
}
