package com.example.client1.Ctrl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    // Handler pour GET
    @GetMapping("/getClient")
    public String getExample(@RequestParam(value = "name", defaultValue = "Client") String name) {
        return String.format("Hello, %s!", name);
    }

}
