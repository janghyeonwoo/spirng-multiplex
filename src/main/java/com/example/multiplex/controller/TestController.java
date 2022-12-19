package com.example.multiplex.controller;

import com.example.multiplex.dto.RequestDto;
import com.example.multiplex.dto.ResponseDto;
import com.example.multiplex.type.StatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@Slf4j
@RequestMapping("test")
@RestController
public class TestController {

    @PostMapping("")
    public ResponseEntity<ResponseDto> getTest(@RequestBody RequestDto requestDto){
        log.info("requestDto : {}", requestDto);
        ResponseDto responseDto = ResponseDto.builder()
                .name("pooney responsedto")
                .statusType(StatusType.DONE)
                .build();
        return ResponseEntity.ok(responseDto);
    }
}
