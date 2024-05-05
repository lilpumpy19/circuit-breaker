package com.example.client.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;

@Configuration

public class CirBreakerConfig {
    @Getter
    private HashMap<String,CircuitBreaker> circuitBreakerMap;

    public CirBreakerConfig() {
        RestTemplate restTemplate = new RestTemplate();
        circuitBreakerMap = new HashMap<>();
        Class<?> clasz = CirBreakerController.class;
        Method[] methods = clasz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            circuitBreakerMap.put(methods[i].getName(), new CircuitBreaker(restTemplate));
        }
    }

}
