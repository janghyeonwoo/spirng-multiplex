package com.example.multiplex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    @ResponseBody
    @PostMapping("/logouts")
    public String logout(HttpSession httpSession){
        System.out.println("================ session ====================");
        System.out.println(httpSession.getId());
        System.out.println("================ session ====================");
        httpSession.invalidate();
        return "ttt";
    }

    @ResponseBody
    @PostMapping("/session")
    public String session(HttpSession httpSession){
        System.out.println("================ session ====================");
        System.out.println(httpSession.getId());
        System.out.println("================ session ====================");
        return "session";
    }
}
