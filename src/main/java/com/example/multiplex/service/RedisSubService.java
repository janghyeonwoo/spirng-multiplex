package com.example.multiplex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubService implements MessageListener {
    public static List<String> messageList = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            System.out.println("subscribe...!!!");
            System.out.println(new String(message.getBody()));
        }catch (Exception e){
            log.error("..",e);
            throw new RuntimeException("Error...!!");
        }
    }
}
