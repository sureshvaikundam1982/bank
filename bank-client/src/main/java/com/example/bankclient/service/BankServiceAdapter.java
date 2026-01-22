package com.example.bankclient.service;

import com.example.bankclient.feign.BankServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class BankServiceAdapter {
    private final BankServiceClient client;

    public BankServiceAdapter(BankServiceClient client) {
        this.client = client;
    }

    @CircuitBreaker(name = "bankService", fallbackMethod = "fallbackGetInfo")
    public String fetchInfo() {
        return client.getInfo();
    }

    // fallback method signature must match the original method with an optional Throwable param
    public String fallbackGetInfo(Throwable t) {
        return "Fallback: bank service unavailable (" + t.getClass().getSimpleName() + ")";
    }
}