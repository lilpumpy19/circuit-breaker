package com.example.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CirBreakerController {
    private final RestTemplate restTemplate;

    @GetMapping("/ok")
    public ResponseEntity<String> callOk() {
        String url = "http://localhost:8081/api/ok"; // URL первого приложения
        return restTemplate.getForEntity(url, String.class);
    }

    @GetMapping("/error")
    public ResponseEntity<String> callError() {
        String url = "http://localhost:8081/api/error"; // URL первого приложения
        return restTemplate.getForEntity(url, String.class);
    }

}
