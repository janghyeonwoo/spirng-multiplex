package com.example.multiplex.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class RedisHashRepositoryImpl<K,V,H> extends AbstractRedisExHashRepository<K,V,H>{
    private final RedisTemplate <K,V> redisTemplate;
    private final HashOperations<K,V,H> hop;

    public RedisHashRepositoryImpl(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hop = redisTemplate.opsForHash();
    }

    @Override
    public Map<V,H> findHash(K key) {
        return hop.entries(key);
    }

    @Override
    public void saveHash(K key, Map<V, H> value) {
        redisTemplate.opsForHash()
                .putAll(key, value);
    }
}
