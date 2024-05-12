package com.nexign.hrs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for check if HRS service is running
 */
@RestController
public class HealthController {
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
