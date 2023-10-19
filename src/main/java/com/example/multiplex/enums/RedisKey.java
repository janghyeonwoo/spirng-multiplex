package com.example.multiplex.enums;

import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public enum RedisKey {

    CODE1(i -> "code1"),
    MEMBER(i -> String.format("member:%s",i)),
    MEMBER_EXPRIE(i -> String.format("member:expire:%s",i));

    public Function<String, String> func;

}
