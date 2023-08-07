package com.example.multiplex.util;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.RedisDto;
import com.example.multiplex.enums.RedisKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void setData(String key, String value, Long expiredTime) {
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    public <T> void setJsonData(String id, T object) throws JsonProcessingException {
        redisTemplate
                .opsForValue()
                .set(id, objectMapper.writeValueAsString(object));
    }

    public <T> void setDataOfList(String id, T object) throws JsonProcessingException {
        redisTemplate
                .opsForList()
                .rightPush(id, objectMapper.writeValueAsString(object));
    }


    public <T> void setZsetData(String id, T object, double score) throws JsonProcessingException {
        redisTemplate
                .opsForZSet()
                .add(id, object, score);
    }


    public Set<Object> getZsetDataLimit(final String id, final Long limit) throws JsonProcessingException {
//        Optional.ofNullable(redisTemplate.opsForZSet().size(id)).orElse(-1L);
        return getZsetData(id,0L, limit);
    }

    public Set<Object> getZsetDataAll(final String id) throws JsonProcessingException {
        return getZsetData(id,0L,-1L);
    }

    private Set<Object> getZsetData(final String id, final Long start, final Long limit){
        return redisTemplate
                .opsForZSet()
                .range(id,start,limit);
    }

    /**
     * {@link} https://redis.io/commands/zrangebyscore/
     *  ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
     *  :) zrange MEMBERSORTED 90 100 BYSCORE LIMIT 0 10 WITHSCORES
     * @param id
     * @return
     * @throws JsonProcessingException
     *
     *
     */
    public List<RedisDto> getZsetDataAllWithScores(final String id) throws JsonProcessingException {
        return Objects.requireNonNull(
                redisTemplate.opsForZSet().rangeByScoreWithScores(id, Double.MIN_VALUE, Double.MAX_VALUE,0,-1))
                .stream()
                .map(i -> new RedisDto(String.valueOf(i.getValue()),String.valueOf(i.getScore())))
                .collect(Collectors.toList());
    }



    public Long getZsetDataWithRank(final String id, final Object data) throws JsonProcessingException {
        return redisTemplate
                .opsForZSet()
                .rank(id,data);
    }

    public <T> List<T> getListData(final String id, final Class<T> classType) {
        List<Object> opsList = redisTemplate.opsForList().range(id, 0, -1);

        assert opsList != null;
        return opsList.stream().map(i -> {
            try {
                return objectMapper.readValue((String) i, classType);
            } catch (JsonProcessingException e) {
                throw new RuntimeException();
            }
        }).collect(Collectors.toList());
    }


    public <T> T getRedisValue(final String key, final Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue((String) redisTemplate.opsForValue().get(key), classType);
    }


}
