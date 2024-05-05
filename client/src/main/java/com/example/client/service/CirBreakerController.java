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
    CirBreakerService cirBreakerService;
    private final String serviceUrl = "http://localhost:8081/api";

    @GetMapping("/ok")
    public ResponseEntity<String> callOk() {
        String url = serviceUrl + "/ok";
        return cirBreakerService.callService(url);
    }

    @GetMapping("/error")
    public ResponseEntity<String> callError() {
        String url = serviceUrl + "/error";
        return cirBreakerService.callService(url);
    }

    @GetMapping("/400error")
    public ResponseEntity<String> callError400() {
        String url = serviceUrl + "/400error";
        return cirBreakerService.callService(url);
    }



}
