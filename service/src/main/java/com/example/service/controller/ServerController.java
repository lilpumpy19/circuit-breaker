package com.example.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServerController {
    @GetMapping("/ok")
    public ResponseEntity<String> ok() {
        return ResponseEntity.ok("Status200: OK");
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        return ResponseEntity.status(500).body("Status500: ERROR");
    }
}
