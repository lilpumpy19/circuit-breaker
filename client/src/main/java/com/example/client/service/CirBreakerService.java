package com.example.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CirBreakerService {
    private final CirBreakerConfig cirBreakerConfig;

    private final RestTemplate restTemplate;
    public ResponseEntity<String> callService(String url, CircuitBreaker circuitBreaker) {
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

    public CircuitBreaker getCircuitBreaker(String methodName) {
        System.out.println(methodName);
        return cirBreakerConfig.getCircuitBreakerMap().get(methodName);
    }
}
