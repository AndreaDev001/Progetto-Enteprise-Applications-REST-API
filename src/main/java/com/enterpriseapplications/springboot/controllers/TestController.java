package com.enterpriseapplications.springboot.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @GetMapping("/secured")
    public ResponseEntity<String> secured() {
        return ResponseEntity.ok("CALLED");
    }
}
