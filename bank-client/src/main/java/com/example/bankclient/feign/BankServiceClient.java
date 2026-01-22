package com.example.bankclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "bank-service")
public interface BankServiceClient {
    @GetMapping("/info")
    String getInfo();
}