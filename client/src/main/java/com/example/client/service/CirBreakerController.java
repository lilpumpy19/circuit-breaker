package com.example.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CirBreakerController {
    private final CirBreakerService cirBreakerService;
    private final String serviceUrl = "http://localhost:8081/api";

    @GetMapping("/method1")
    public ResponseEntity<String> method1(@RequestParam boolean flag) {
        String url = serviceUrl + "/method1?flag=" + flag;
        return cirBreakerService.callService(url);
    }

    @GetMapping("/method2")
    public ResponseEntity<String> method2(@RequestParam boolean flag) {
        String url = serviceUrl + "/method2?flag=" + flag;
        return cirBreakerService.callService(url);
    }

    @GetMapping("/400error")
    public ResponseEntity<String> callError400() {
        String url = serviceUrl + "/400error";
        return cirBreakerService.callService(url);
    }



}
