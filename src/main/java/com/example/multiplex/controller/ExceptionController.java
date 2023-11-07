package com.example.multiplex.controller;

import com.example.multiplex.exception.ClientException;
import com.example.multiplex.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exception")
public class ExceptionController {

    @GetMapping("client-exception")
    public ResponseEntity<?> clientException(){
        if(true) throw new ClientException(ErrorCode.BAD_REQUEST);
        return ResponseEntity.ok().build();
    }
}
