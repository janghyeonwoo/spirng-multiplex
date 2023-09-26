package com.example.multiplex.util;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.RedisDto;
import com.example.multiplex.enums.RedisKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private final long startIndex = 0;
    private final long endIndex = -1;


    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public <T> void setJsonData(String id, T object) throws JsonProcessingException {
        redisTemplate
                .opsForValue()
                .set(id, objectMapper.writeValueAsString(object));
    }

    /**
     * List 데이터에 값 넣기
     *
     * @param id
     * @param object
     * @param <T>
     * @throws JsonProcessingException
     */
    public <T> void setDataOfList(String id, T object) throws JsonProcessingException {
        redisTemplate
                .opsForList()
                .rightPush(id, objectMapper.writeValueAsString(object));
    }

    /**
     * Zset에 데이터 넣기
     * Zset : 정렬 가능한 자료구조
     *
     * @param id
     * @param object
     * @param score
     * @param <T>
     * @throws JsonProcessingException
     */
    public <T> void setZsetData(String id, T object, double score) {
        redisTemplate
                .opsForZSet()
                .add(id, object, score);
    }


    public Set<Object> getZsetDataLimit(final String id, final Long end) {
//        Optional.ofNullable(redisTemplate.opsForZSet().size(id)).orElse(-1L);
        return getZsetData(id, startIndex, end);
    }

    public Set<Object> getZsetDataPaging(final String id, final long start, final long end) {
        return getZsetData(id, start, end);
    }

    public Set<Object> getZsetDataAll(final String id) {
        return getZsetData(id, startIndex, endIndex);
    }

    private Set<Object> getZsetData(final String id, final Long start, final Long end) {
        return redisTemplate
                .opsForZSet()
                .range(id, start, end);
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

    public List<RedisDto> getZsetRedisDtoAllWithScores(final String id) {
        return toRedisDtoList(getZsetDataWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, endIndex));
    }

    public List<RedisDto> getZsetRedisDtoPagingWithScores(final String id, final long start, final long end) {
        return toRedisDtoList(getZsetDataWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, start, end));
    }

    public List<RedisDto> getZsetRedisDtoLimitWithScores(final String id, final long end) {
        return toRedisDtoList(getZsetDataWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE, startIndex, end));
    }

    private Set<ZSetOperations.TypedTuple<Object>> getZsetDataWithScores(final String id, double min, double max, final long start, final long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(id, min, max, start, end);
    }

    private List<RedisDto> toRedisDtoList(Set<ZSetOperations.TypedTuple<Object>> typedTuples) {
        return Objects.requireNonNull(typedTuples)
                .stream()
                .map(i -> new RedisDto(String.valueOf(i.getValue()), String.valueOf(i.getScore())))
                .collect(Collectors.toList());
    }


    /**
     * 특정 데이터 랭킹 조회
     *
     * @param id
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    public Long getZsetDataWithRank(final String id, final Object data) throws JsonProcessingException {
        return redisTemplate
                .opsForZSet()
                .rank(id, data);
    }


    /**
     * List 데이터를 페이징하여 받기
     *
     * @param id        키
     * @param classType 리턴 받을 타입
     * @param start     시작 index
     * @param end       끝 index
     * @param <T>
     * @return
     */
    public <T> List<T> getListDataPaging(final String id, final Class<T> classType, long start, long end) {
        return getListData(id, classType, start, end);
    }

    /**
     * List 데이터 전체 받기
     *
     * @param id        키
     * @param classType 리턴 받을 타입
     * @param <T>
     * @return
     */
    public <T> List<T> getListDataAll(final String id, final Class<T> classType) {
        return getListData(id, classType, 0, -1);
    }

    public <T> List<T> getListDataLimit(final String id, final Class<T> classType, final long end) {
        return getListData(id, classType, 0, end);
    }

    private <T> List<T> getListData(final String id, final Class<T> classType, final long start, final long end) {
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
