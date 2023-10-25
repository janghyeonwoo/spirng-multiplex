package com.example.multiplex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPubService {
    private final RedisTemplate<String,Object> redisTemplate;

    public void publish (ChannelTopic topic, String message){
        redisTemplate.convertAndSend(topic.getTopic(),message);
    }

    public <T> void publish (ChannelTopic topic, T message){
        redisTemplate.convertAndSend(topic.getTopic(),message);
    }
}
