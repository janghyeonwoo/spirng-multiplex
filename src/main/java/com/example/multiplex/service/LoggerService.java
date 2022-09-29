package com.example.multiplex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoggerService {
    private final LoggerSubService loggerSubService;
    public void callLog(){
        loggerSubService.callLoggerSubService();
        log.info("callLog 호출");
    }
}
