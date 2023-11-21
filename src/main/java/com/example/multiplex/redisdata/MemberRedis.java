package com.example.multiplex.redisdata;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@RedisHash(value = "redismember", timeToLive = 30)
@Getter
public class MemberRedis {

    @Id
    private String id;
    private int age;

}
