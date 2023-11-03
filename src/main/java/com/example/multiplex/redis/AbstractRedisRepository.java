package com.example.multiplex.redis;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractRedisRepository<K,V> implements RedisExRepository<K,V>{
    protected final RedisTemplate<K, V> redisTemplate;

    public AbstractRedisRepository(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Boolean deleteById(K key) {
        return redisTemplate.delete(key);
    }
}
