package com.example.multiplex.redis;


import com.example.multiplex.dto.RedisDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
@Repository
public class RedisRepositoryImpl<K, V> extends AbstractRedisRepository<K, V> {
    private final RedisTemplate<K, V> redisTemplate;
    private final ObjectMapper objectMapper;
    private final long startIndex = 0;
    private final long endIndex = -1;

    public RedisRepositoryImpl(RedisTemplate<K, V> redisTemplate, ObjectMapper objectMapper) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }





    @Override
    public Boolean saveIfAbsent(K key, V value) {
        return redisTemplate.boundValueOps(key).setIfAbsent(value);
    }


    @Override
    public void save(K key, V value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }


    @Override
    public Boolean pushToList(K key, V value) {
        return pushToList(key,value,-1);
    }

    @Override
    public Boolean pushToList(K key, V value, long time) {
        try {
            redisTemplate
                    .opsForList()
                    .rightPush(key, value);


            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis setList Error");
        }
        return false;
    }


    @Override
    public void saveZSet(K key, V value, double score) {
        redisTemplate
                .opsForZSet()
                .add(key, value, score);
    }

    // ############################


    @Override
    public List<RedisDto> findZSetWithScores(K key) {
        return toRedisDtoList(findZSetWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, endIndex));
    }

    @Override
    public List<RedisDto> findZSetWithScores(K key, long start, long end) {
        return toRedisDtoList(findZSetWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, start, end));
    }

    @Override
    public List<RedisDto> findZSetWithScores(K key, long end) {
        return toRedisDtoList(findZSetWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, end));
    }

    @Override
    public Set<ZSetOperations.TypedTuple<V>> findZSetWithScores(K key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }


    /**
     * Redis 데이터를 RedisDto로 변경
     * @param typedTuples
     * @return
     */
    public List<RedisDto> toRedisDtoList(Set<ZSetOperations.TypedTuple<V>> typedTuples) {
        return Objects.requireNonNull(typedTuples)
                .stream()
                .map(i -> new RedisDto(String.valueOf(i.getValue()), String.valueOf(i.getScore())))
                .collect(Collectors.toList());
    }



    @Override
    public Set<V> findZSetLimit(K key, long end) {
        return findZSet(key,startIndex, end);
    }

    @Override
    public Set<V> findZSetPaging(K key, long start, long end) {
        return findZSet(key,start,end);
    }

    @Override
    public Set<V> findZSet(K key, Long start, Long end) {
            return redisTemplate
                .opsForZSet()
                .range(key, start, end);
    }


    @Override
    public Long findZSetWithRank(K key, V value) {
         return redisTemplate
                .opsForZSet()
                .rank(key, value);
    }




    @Override
    public List<V> findListAll(K key, long start, long end) {
        return findList(key,startIndex, endIndex);
    }

    @Override
    public List<V> findListLimit(K key, long end) {
        return findList(key,startIndex, end);
    }

    @Override
    public List<V> findList(K key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public V findById(K key) {
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public void pushToList(K key, List<V> value) {
        for(V item : value){
            pushToList(key,item);
        }
    }

    @Override
    public void pushToList(K key, List<V> value, Long time) {
         for(V item : value){
            pushToList(key,item,time);
        }
    }
}
