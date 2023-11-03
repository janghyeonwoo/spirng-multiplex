package com.example.multiplex.redis;

import com.example.multiplex.dto.RedisDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Set;

public interface RedisExRepository<K,V> extends RedisRepository<K,V>{
    Boolean pushToList(K key, V value, long time);
    void save(K key, V value, long time);

    void pushToList(K key, List<V> value);
    void pushToList(K key, List<V> value, Long time);

    //##################################

    List<RedisDto> findZSetWithScores(K key);
    List<RedisDto> findZSetWithScores(K key, long start, long end);
    List<RedisDto> findZSetWithScores(K key, long end);



    Set<V> findZSetLimit(K key, long end);
    Set<V> findZSetPaging(K key, long start, long end);

    Long findZSetWithRank(K key , V value);

    List<V> findListAll(K key, long start, long end);
    List<V> findListLimit(K key, long end);


}
