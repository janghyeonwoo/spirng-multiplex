package com.example.multiplex.enums;

public enum RedisKey {
    MEMBER(),
    MEMBEREXPIRE();

    public String getKey(String id){
        return String.format("%s:%s",this.name(), id);
    }

}
