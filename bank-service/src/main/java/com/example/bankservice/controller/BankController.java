package com.example.bankservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Value("${bank.welcome-message:Welcome to Bank Service}")
    private String message;

    @GetMapping("/info")
    public String info() {
        return message;
    }
}