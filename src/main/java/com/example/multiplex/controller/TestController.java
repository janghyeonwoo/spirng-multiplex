package com.example.multiplex.controller;

import com.example.multiplex.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("test")
@RestController
public class TestController {

    private final TestService testService;

    @GetMapping("/getTest")
    public ResponseEntity<?> getTest(@RequestParam("boardIdx") Integer boardIdx){
        return ResponseEntity.ok(testService.getBoard(boardIdx));
    }
}
