package com.example.multiplex.redis;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractRedisRepository<K,V> implements RedisExRepository<K,V>{

}
