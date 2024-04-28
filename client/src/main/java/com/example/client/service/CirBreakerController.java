package com.example.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CirBreakerController {
    private final RestTemplate restTemplate;
    private final CircuitBreaker circuitBreaker;

    @GetMapping("/ok")
    public ResponseEntity<String> callOk() {
        String url = "http://localhost:8081/api/ok"; // URL первого приложения
        return this.callService(url);
    }

    @GetMapping("/error")
    public ResponseEntity<String> callError() {
        String url = "http://localhost:8081/api/error"; // URL первого приложения
        return this.callService(url);
    }

    @GetMapping("/400error")
    public ResponseEntity<String> callError400() {
        String url = "http://localhost:8081/api/400error"; // URL первого приложения
        return this.callService(url);
    }

    private ResponseEntity<String> callService(String url) {
        if (circuitBreaker.checkState()) {
            try {
                ResponseEntity<String> status = restTemplate.getForEntity(url, String.class);
                circuitBreaker.ok();
                return status;
            }catch (HttpServerErrorException e){
                circuitBreaker.error();
                return ResponseEntity.status(500).body("Service500");
            }

        }else {
            return ResponseEntity.status(503).body("Service is unavailable");
        }
    }

}
