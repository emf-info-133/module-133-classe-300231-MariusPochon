package com.example.marque_paf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.example.marque_paf")

public class MarquePafApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MarquePafApplication.class, args);
    }
}