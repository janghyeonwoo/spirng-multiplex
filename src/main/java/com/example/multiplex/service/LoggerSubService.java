package com.example.multiplex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggerSubService {

    public void callLoggerSubService(){
        log.info("callLoggerSubService 호출!!");
    }

}
