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

    /**
     * Set에 넣기
     * @param key
     * @param value
     * @param expiredTime
     */
    public void setValue(String key, Object value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 해당 키 존재 여부 확인
     * @param key
     * @return
     */
    public boolean containsKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e){
            log.error("Redis Error...");
        }
        return false;
    }

    /**
     * Set json value 넣기
     * @param key
     * @param value
     * @param <T>
     * @throws JsonProcessingException
     */
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
    public <T> boolean setList(String key, T value) {
        return setList(key,value,-1);
    }


    public <T> boolean setList(String key, T value, long time) {
        try {
            redisTemplate
                    .opsForList()
                    .rightPush(key, objectMapper.writeValueAsString(value));


            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis setList Error");
        }
        return false;
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


    /**
     * 만료 시간 설정
     * @param id
     * @param value
     * @return
     */
    public Boolean setSetExpire(final String id, final String value){
        return setSetExpireTime(id, value,60, TimeUnit.SECONDS);
    }

    /**
     * 키를 고정으로 하고 사용할때 유용할 듯 한데 필요없을 듯하다.
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
     *
     * 스코어와 함께 조회
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


    /**
     * Redis 데이터를 RedisDto로 변경
     * @param typedTuples
     * @return
     */
    public List<RedisDto> toRedisDtoList(Set<ZSetOperations.TypedTuple<Object>> typedTuples) {
        return Objects.requireNonNull(typedTuples)
                .stream()
                .map(i -> new RedisDto(String.valueOf(i.getValue()), String.valueOf(i.getScore())))
                .collect(Collectors.toList());
    }


    /**
     * Set limit 조회
     * @param id
     * @param end
     * @return
     */
    public Set<Object> getZSetValueLimit(final String id, final Long end) {
//        Optional.ofNullable(redisTemplate.opsForZSet().size(id)).orElse(-1L);
        return getZSetValue(id, startIndex, end);
    }

    /**
     * Set 구간 조회
     * @param id
     * @param start
     * @param end
     * @return
     */
    public Set<Object> getZSetValuePaging(final String id, final long start, final long end) {
        return getZSetValue(id, start, end);
    }

    /**
     * Set 전체 조회
     * @param id
     * @return
     */
    public Set<Object> getZSetValueAll(final String id) {
        return getZSetValue(id, startIndex, endIndex);
    }

    /**
     * Set 조회
     * @param id
     * @param start
     * @param end
     * @return
     */
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
        return getList(id,0,-1,classType);
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
        return getList(id,0, end, classType);
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
    public <T> List<T> getList(final String id, final long start, final long end, final Class<T> classType) {
        List<Object> opsList = getList(id, start, end);

        assert opsList != null;
        return opsList.stream().map(i -> {
            try {
                return objectMapper.readValue(String.valueOf(i), classType);
            } catch (IOException e) {
                throw new RuntimeException("JSON PARSING ERROR.. TO REDIS");
            }
        }).collect(Collectors.toList());
    }

    public List<Object> getList(final String id, final long start, final long end){
        return redisTemplate.opsForList().range(id, start, end);
    }



    public <T> T getValue(final String key, final Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue((String) getValue(key), classType);
    }

    public Object getValue(final String key){
        return redisTemplate.opsForValue().get(key);
    }


}
