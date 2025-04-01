package com.example.rest1.Ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    // Handler pour GET
    @GetMapping("/getRest1")
    public String getExample(@RequestParam(value = "name", defaultValue = "rest1") String name) {
        return String.format("Hello, %s!", name);
    }

}
