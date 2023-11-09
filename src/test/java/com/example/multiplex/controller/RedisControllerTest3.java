package com.example.multiplex.controller;

import com.example.multiplex.dto.Code2Dto;
import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.redis.AbstractRedisExHashRepository;
import com.example.multiplex.redis.RedisRepositoryImpl;
import com.example.multiplex.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisControllerTest3 {

    private final String REDIS_MEMBER_LIST_KEY = "MEMBERLIST";
    private final String REDIS_MEMBER_SORTED_SET_KEY = "MEMBERSORTED";
//    private final String REDIS_MEMBER_SET_EXPIRE_KEY = "MEMBER-EXPIRE";


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RedisRepositoryImpl<String,Object> redisRepository;

    @Autowired
    private AbstractRedisExHashRepository<String, Long, List<Code2Dto>> redisHashRepository;

    @DisplayName("키여부 확인")
    @Test
    public void containsKey(){
        System.out.println(redisRepository.containsKey("member:80649"));
    }


    @DisplayName("set 하기")
    @Test
    public void saveSet(){
        redisRepository.save("test:1111","ssss");
    }

    @DisplayName("set expire")
    @Test
    public void saveSetExpire(){
        redisRepository.save("test:expire:1111","ssss", 30000 );
    }


    @DisplayName("List에 PUSH 하기")
    @Test
    public void pushToList(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.pushToList("test:push:1",i));
    }


    @DisplayName("List에 EXPIRE PUSH 하기")
    @Test
    public void pushToListExpire(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.pushToList("test:push:expire:1",i,3000));
    }


    @DisplayName("set Zset 하기")
    @Test
    public void saveZset(){
        IntStream.rangeClosed(1,10).forEach(i -> redisRepository.saveZSet("test:zset:1111", "member" + i, i));
    }


    @DisplayName("set Hash 하기")
    @Test
    public void saveHash(){
        Map<Long, List<Code2Dto>> code2Map = IntStream.rangeClosed(1,10).mapToObj(i -> Code2Dto.builder().code1Idx(1L).code2Idx((long) i).value("value_"+i).build()).collect(Collectors.groupingBy(Code2Dto::getCode2Idx));

        redisHashRepository.saveHash("hash:1", code2Map);
    }




    @DisplayName("find Hash 하기")
    @Test
    public void findHash(){
        Map<Long, List<Code2Dto>> hash = redisHashRepository.findHash("hash:1");
        System.out.println(hash.get(1L));
    }


    @DisplayName("findZSetWithScores")
    @Test
    public void findZSetWithScores(){
        System.out.println(redisRepository.findZSetWithScores("test:zset:1111"));
    }


    @DisplayName("setList")
    @Test
    public void setList() {
        redisRepository.pushToList("push:list",getMembers(10));
    }

    public List<MemberDto> getMembers(int num){
        return IntStream.rangeClosed(1,num).mapToObj(i -> MemberDto.builder().age(num).name("ss_"+ i).build())
                .collect(Collectors.toList());
    }

}


