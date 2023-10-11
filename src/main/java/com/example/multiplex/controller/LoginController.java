package com.example.multiplex.controller;

import com.amazonaws.Response;
import com.example.multiplex.anno.LoginUser;
import com.example.multiplex.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RequiredArgsConstructor
@RestController
public class LoginController {


    @GetMapping
    public ResponseEntity<?> getLoginMember(@LoginUser MemberDto memberDto){
        return ResponseEntity.ok(memberDto);
    }

}
