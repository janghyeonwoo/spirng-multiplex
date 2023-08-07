package com.example.multiplex.controller;

import com.example.multiplex.entity.Member;
import com.example.multiplex.repository.MemberRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisControllerTest {
    @Autowired
    private MemberRepository memberRepository;

    @Rollback(value = false)
    @BeforeTestClass
    public void beforeAll(){
        IntStream.range(1,100).forEach(i -> {
            memberRepository.save(createMember(1,"name"+i, "id" + i));
        });
    }

    private Member createMember(int age, String name, String id) {
        return Member.builder()
                .age(age)
                .name(name)
                .id(id).build();

    }


    @Test
    void dbToRedis() {
        List<Member> findMemberList = memberRepository.findAll();
        findMemberList.stream().forEach(i -> {
            System.out.println(i);
        });

    }


}
