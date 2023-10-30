package com.example.multiplex.controller;

import com.example.multiplex.dto.MemberEventDto;
import com.example.multiplex.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/message")
    public ResponseEntity<?> sendMemberMessage(@RequestBody MemberEventDto memberEventDto){
        memberService.sendMemberMessage(memberEventDto);
        return ResponseEntity.ok().build();
    }
}
