package com.example.multiplex.controller;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.RedisDto;
import com.example.multiplex.entity.Member;
import com.example.multiplex.enums.RedisKey;
import com.example.multiplex.redis.RedisExRepository;
import com.example.multiplex.redis.RedisRepository;
import com.example.multiplex.repository.MemberRepository;
import com.example.multiplex.util.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisControllerTest3 {

    private final String REDIS_MEMBER_LIST_KEY = "MEMBERLIST";
    private final String REDIS_MEMBER_SORTED_SET_KEY = "MEMBERSORTED";
//    private final String REDIS_MEMBER_SET_EXPIRE_KEY = "MEMBER-EXPIRE";


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RedisExRepository<String,Object> redisRepository;

    @DisplayName("키여부 확인")
    @Test
    public void containsKey(){
        System.out.println(redisRepository.containsKey("member:80649"));
    }


    @DisplayName("set 하기")
    @Test
    public void saveSet(){
        redisRepository.save("test:1111","ssss");
    }

    @DisplayName("set expire")
    @Test
    public void saveSetExpire(){
        redisRepository.save("test:expire:1111","ssss", 30000 );
    }


    @DisplayName("List에 PUSH 하기")
    @Test
    public void pushToList(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.pushToList("test:push:1",i));
    }


    @DisplayName("List에 EXPIRE PUSH 하기")
    @Test
    public void pushToListExpire(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.pushToList("test:push:expire:1",i,3000));
    }


    @DisplayName("set Zset 하기")
    @Test
    public void saveZset(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.saveZSet("test:zset:1111", "member" + i, i));
    }





}


