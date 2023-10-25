package com.example.multiplex.controller;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.MessageDto;
import com.example.multiplex.dto.RedisDto;
import com.example.multiplex.entity.Member;
import com.example.multiplex.service.MemberService;
import com.example.multiplex.service.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private final StringRedisTemplate stringRedisTemplate;
    private final MemberService memberService;
    private final RedisPubService redisPubService;


    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
    }

    @PostMapping(value = "/setRedisStringValue")
    public void setRedisStringValue(@RequestBody RedisDto redisDto) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(redisDto.getKey(),redisDto.getValue());
    }

    @GetMapping("/getSessionId")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }

    @PostMapping("/save/member")
    public String addMember(@RequestParam("name") String name){
        memberService.addMember(name);
        return name;
    }

    //#id -> request의 필드명
    @Cacheable(key = "#id", value = "memberInfo")
    @GetMapping("/member")
    public MemberDto findMember(@RequestParam("id") String id) {
        return memberService.findMember(id);
    }
    //#memberDto.id -> request의 필드명
    @CachePut(key = "#memberDto.id", value = "memberInfo")
    @PutMapping("/update/member")
    public MemberDto updateMember(@RequestBody MemberDto memberDto){
        return memberService.updateMember(memberDto);
    }


    @PostMapping("/message")
    public String saveMessage(@RequestBody MessageDto messageDto){
        redisPubService.publish(new ChannelTopic("topic1"),messageDto.getMessage());
        return "OK";
    }


}
