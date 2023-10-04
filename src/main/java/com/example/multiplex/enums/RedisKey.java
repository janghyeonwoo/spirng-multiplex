package com.example.multiplex.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RedisKey {
    MEMBER("MEMBER"),
    MEMBEREXPIRE("MEMBER:EXPRIE");
    private String code;


    public String getKey(String id){
        return String.format("%s:%s",this.code, id);
    }

}
