package com.example.multiplex.controller;

import com.example.multiplex.service.AsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AsyncController {


    private final AsyncService asyncService;


    @GetMapping("/async")
    public String goAsync() {
        asyncService.onAsync();
        String str = "start ---- async";
        log.info(str);
        return str;
    }

    @GetMapping("/sync")
    public String goSync() {
        asyncService.onSync();
        String str = "start ---- sync";
        log.info(str);
        return str;
    }

}
