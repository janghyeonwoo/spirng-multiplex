package com.example.multiplex.redis;

import com.example.multiplex.dto.RedisDto;
import org.springframework.data.redis.core.ZSetOperations;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisRepository<K,V> {
    void save(K key, V value);

    Boolean saveIfAbsent(K key, V value);
    Boolean pushToList(K key, V value);
    Boolean containsKey(K key);

    void saveZSet(K key, V value, double score);


    //###############################
    Set<ZSetOperations.TypedTuple<V>> findZSetWithScores(K key, double min, double max, final long start, final long end);
    Set<V> findZSet(K Key, final Long start, final Long end);

    List<V> findList(K key, long start, long end);

    V findById(K key);

    Boolean deleteById(K key);
}
