package com.example.multiplex.controller;

import com.example.multiplex.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("security")
@RestController
public class SecurityController {


    @GetMapping
    public ResponseEntity<?> testSecurity(@AuthenticationPrincipal MemberDto memberDto){
        log.info("start!!!! !! {}", memberDto);
        return ResponseEntity.ok().build();
    }
}
