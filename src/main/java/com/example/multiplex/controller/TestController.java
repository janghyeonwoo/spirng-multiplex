package com.example.multiplex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("test")
@RestController
public class TestController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping
    public String getPassword(@RequestParam("password") String password){
        return bCryptPasswordEncoder.encode(password);
    }
}
