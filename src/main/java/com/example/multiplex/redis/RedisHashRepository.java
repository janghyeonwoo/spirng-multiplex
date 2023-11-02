package com.example.multiplex.redis;

import java.util.Map;

public interface RedisHashRepository<K,V,H> {
    void saveHash(K key, Map<V,H> value);

    Map<V,H> findHash(K key);



}
