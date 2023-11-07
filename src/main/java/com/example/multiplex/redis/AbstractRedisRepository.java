package com.example.multiplex.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public abstract class AbstractRedisRepository<K,V> implements RedisExRepository<K,V>{
    protected final RedisTemplate<K, V> redisTemplate;

    public AbstractRedisRepository(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Boolean deleteById(K key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void save(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Boolean containsKey(K key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            log.error("Redis Error.....");
            return false;
        }
    }
}
