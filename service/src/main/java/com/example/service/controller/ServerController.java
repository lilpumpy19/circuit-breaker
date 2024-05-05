package com.example.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ServerController {
    @GetMapping("/method1")
    public ResponseEntity<String> method1(@RequestParam boolean flag) {
        if (flag) {
            return ResponseEntity.ok("Status200: OK");
        }else {
            return ResponseEntity.status(500).body("Status500: ERROR");
        }
    }

    @GetMapping("/method2")
    public ResponseEntity<String> method2(@RequestParam boolean flag) {
        if (flag) {
            return ResponseEntity.ok("Status200: OK");
        }else {
            return ResponseEntity.status(500).body("Status500: ERROR");
        }
    }

    @GetMapping("/400error")
    public ResponseEntity<String> error400() {
        return ResponseEntity.status(400).body("Status400: ERROR");
    }
}
