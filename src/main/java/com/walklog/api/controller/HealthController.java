package com.walklog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "Walklog API is running! 🚀";
    }

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}
