package com.example.multiplex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Slf4j
@Service
public class AsyncService {

    @Async
    public void onAsync() {
        try{
            Thread.sleep(5000);
            log.info("onAsync : {}" , Thread.currentThread().getPriority());
        }catch (InterruptedException e){
            log.info("..",e);
        }
    }

    public void onSync() {
        try{
            Thread.sleep(5000);
            log.info("onSync: {}" , Thread.currentThread().getPriority());
        } catch (InterruptedException e) {
            log.info("..",e);
        }
    }


}
