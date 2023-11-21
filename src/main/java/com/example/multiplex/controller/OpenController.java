package com.example.multiplex.controller;

import com.example.multiplex.dto.RequestDto;
import com.example.multiplex.open.StoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("open")
@RestController
public class OpenController {
    private final StoreClient storeClient;

    @GetMapping
    public String getOpen(){
        return storeClient.getHello();
    }

    @GetMapping("/hi")
    public String getHi(){
        RequestDto dto = RequestDto.builder().age(10).name("HI").build();
        return storeClient.getHi(dto);
    }
}
