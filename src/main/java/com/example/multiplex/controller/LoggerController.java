package com.example.multiplex.controller;

import com.example.multiplex.service.LoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("log")
@RestController
public class LoggerController {
    private final LoggerService loggerService;

    @GetMapping("test")
    public String loggerTest() {
        log.warn("warn level!!");
        log.error("error level!!");
        log.info("info level!!");
        log.debug("debug level!!");
        log.trace("trace level!!");
        loggerService.callLog();
        return "logger test";
    }
}
