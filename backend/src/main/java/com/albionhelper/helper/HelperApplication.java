package com.albionhelper.helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HelperApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelperApplication.class, args);
    }
}