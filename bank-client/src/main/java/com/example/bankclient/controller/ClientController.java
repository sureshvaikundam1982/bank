package com.example.bankclient.controller;

import com.example.bankclient.service.BankServiceAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final BankServiceAdapter adapter;

    public ClientController(BankServiceAdapter adapter) {
        this.adapter = adapter;
    }

    @GetMapping("/bank-info")
    public String bankInfo() {
        return adapter.fetchInfo();
    }
}