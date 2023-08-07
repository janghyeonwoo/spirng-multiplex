package com.example.multiplex.enums;

public enum RedisKey {
    MEMBER();

    public String getKey(String id){
        return String.format("%s:%s",this.name(), id);
    }

}
