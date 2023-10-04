package com.example.multiplex.util;

import com.example.multiplex.dto.RedisDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final long startIndex = 0;
    private final long endIndex = -1;


    public void setValue(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public boolean containsKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e){
            log.error("Redis Error...");
        }
        return false;
    }

    public <T> void setJsonValue(String key, T value) throws JsonProcessingException {
        redisTemplate
                .opsForValue()
                .set(key, objectMapper.writeValueAsString(value));
    }

    /**
     * List 데이터에 값 넣기
     *
     * @param key
     * @param value
     * @param <T>
     * @throws JsonProcessingException
     */
    public <T> void setList(String key, T value) throws JsonProcessingException {
        setList(key,value,-1);
    }


    public <T> void setList(String key, T value, long time) throws JsonProcessingException {
        redisTemplate
                .opsForList()
                .rightPush(key, objectMapper.writeValueAsString(value));

        if(time > 0){
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
        }
    }

    /**
     * Zset에 데이터 넣기
     * Zset : 정렬 가능한 자료구조
     *
     * @param key
     * @param value
     * @param score
     * @param <T>
     * @throws JsonProcessingException
     */
    public <T> void setZSetValue(String key, T value, double score) {
        redisTemplate
                .opsForZSet()
                .add(key, value, score);
    }



    public Boolean setSetExpire(final String id, final String value){
        return setSetExpireTime(id, value,60, TimeUnit.SECONDS);
    }

    /**
     * @link {https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/BoundValueOperations.html#setIfAbsent-V-}
     * @param id  키
     * @param value 값
     * @param time EXPIRE 시간
     * @param timeUnit EXPIRE 단위
     * @return
     */
    public Boolean setSetExpireTime(final String id, final String value, long time, TimeUnit timeUnit){
        return redisTemplate.boundValueOps(id)
                .setIfAbsent(value,time, timeUnit);
    }



    /**
     * {@link} https://redis.io/commands/zrangebyscore/
     * ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     * :) zrange MEMBERSORTED 90 100 BYSCORE LIMIT 0 10 WITHSCORES
     *
     * @param id
     * @return
     * @throws JsonProcessingException
     */

    public List<RedisDto> getZSetWithScores(final String id) {
        return toRedisDtoList(getZSetWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, endIndex));
    }

    public List<RedisDto> getZSetWithScores(final String id, final long start, final long end) {
        return toRedisDtoList(getZSetWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, start, end));
    }

    public List<RedisDto> getZSetWithScores(final String id, final long end) {
        return toRedisDtoList(getZSetWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, end));
    }

    public Set<ZSetOperations.TypedTuple<Object>> getZSetWithScores(final String id, double min, double max, final long start, final long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(id, min, max, start, end);
    }

    private List<RedisDto> toRedisDtoList(Set<ZSetOperations.TypedTuple<Object>> typedTuples) {
        return Objects.requireNonNull(typedTuples)
                .stream()
                .map(i -> new RedisDto(String.valueOf(i.getValue()), String.valueOf(i.getScore())))
                .collect(Collectors.toList());
    }




    public Set<Object> getZSetValueLimit(final String id, final Long end) {
//        Optional.ofNullable(redisTemplate.opsForZSet().size(id)).orElse(-1L);
        return getZSetValue(id, startIndex, end);
    }

    public Set<Object> getZSetValuePaging(final String id, final long start, final long end) {
        return getZSetValue(id, start, end);
    }

    public Set<Object> getZSetValueAll(final String id) {
        return getZSetValue(id, startIndex, endIndex);
    }

    public Set<Object> getZSetValue(final String id, final Long start, final Long end) {
        return redisTemplate
                .opsForZSet()
                .range(id, start, end);
    }


    /**
     * 특정 데이터 랭킹 조회
     *
     * @param id
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    public Long getZSetWithRank(final String id, final Object data) throws JsonProcessingException {
        return redisTemplate
                .opsForZSet()
                .rank(id, data);
    }


    /**
     * List 데이터 전체 받기
     *
     * @param id        키
     * @param classType 리턴 받을 타입
     * @param <T>
     * @return
     */
    public <T> List<T> getListAll(final String id, final Class<T> classType) {
        return getList(id, classType, 0, -1);
    }

    /**
     * List Limit
     * @param id
     * @param classType
     * @param end
     * @param <T>
     * @return
     */
    public <T> List<T> getListLimit(final String id, final Class<T> classType, final long end) {
        return getList(id, classType, 0, end);
    }

    /**
     * List 조회
     * @param id
     * @param classType
     * @param start
     * @param end
     * @param <T>
     * @return
     */
    public <T> List<T> getList(final String id, final Class<T> classType, final long start, final long end) {
        List<Object> opsList = redisTemplate.opsForList().range(id, start, end);

        assert opsList != null;
        return opsList.stream().map(i -> {
            try {
                return objectMapper.readValue(String.valueOf(i), classType);
            } catch (IOException e) {
                throw new RuntimeException("JSON PARSING ERROR.. TO REDIS");
            }
        }).collect(Collectors.toList());
    }


    public <T> T getRedisValue(final String key, final Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue((String) redisTemplate.opsForValue().get(key), classType);
    }


}
