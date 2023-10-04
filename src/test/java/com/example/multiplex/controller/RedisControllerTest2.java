package com.example.multiplex.controller;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.RedisDto;
import com.example.multiplex.entity.Member;
import com.example.multiplex.enums.RedisKey;
import com.example.multiplex.repository.MemberRepository;
import com.example.multiplex.util.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisControllerTest2 {

    private final String REDIS_MEMBER_LIST_KEY = "MEMBERLIST";
    private final String REDIS_MEMBER_SORTED_SET_KEY = "MEMBERSORTED";
//    private final String REDIS_MEMBER_SET_EXPIRE_KEY = "MEMBER-EXPIRE";


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RedisUtils redisUtils;

    @Rollback(false)
    @BeforeAll
    public void beforeAll() {
        System.out.println("===============beforeAll");
        IntStream.range(1, 100).forEach(i -> {
            memberRepository.save(createMember(i, "name" + i, "id" + i));
        });
        System.out.println("===============beforeAll End");
    }

    private Member createMember(int age, String name, String id) {
        return Member.builder()
                .age(age)
                .name(name)
                .id(id).build();

    }

    //#####################################################################
    //                    REDIS DATA SETTINGS
    //#####################################################################
    @Test
    void dbMemberToRedis() {
        List<Member> findMemberList = memberRepository.findAll();
        findMemberList.forEach(i -> {
            try {
                MemberDto memberDto = i.getMemberDto();
                redisUtils.setJsonValue(RedisKey.MEMBER.getKey(memberDto.getId()), memberDto);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }


    @Test
    void dbMemberListToRedis() {
        List<Member> findMemberList = memberRepository.findAll();
        findMemberList.forEach(i -> {
            try {
                MemberDto memberDto = i.getMemberDto();
                redisUtils.setList(REDIS_MEMBER_LIST_KEY, memberDto,3000);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }



    @Test
    void setZsetDataToRedis() throws JsonProcessingException {
        List<Member> findMemberList = memberRepository.findAll();
        for (Member dto : findMemberList) {
            redisUtils.setZSetValue(REDIS_MEMBER_SORTED_SET_KEY, dto.getMemberDto().getId(), dto.getAge());

        }
    }

    /**
     *
     * TTL 적용
     */
    @Test
    void setSetExpire() throws JsonProcessingException {
        List<Member> findMemberList = memberRepository.findAll();
        Member findMember = findMemberList.get(0);
        boolean check = redisUtils.setSetExpire(RedisKey.MEMBEREXPIRE.getKey(findMember.getId()),"OK");
        System.out.println("=============== check ================");
        System.out.println("Key : " + RedisKey.MEMBEREXPIRE.getKey(findMember.getId()));
        System.out.println("Member : " + findMember.getId());
        System.out.println("response : " + check);
    }



    //#####################################################################
    //                    REDIS 데이터 조회
    //#####################################################################
    @DisplayName("List 데이터 조회")
    @Test
    void readMemberListFromRedis() {
        List<MemberDto> memberDtoList = redisUtils.getListAll(REDIS_MEMBER_LIST_KEY, MemberDto.class);
        System.out.println(memberDtoList);
    }


    @DisplayName("Zset를 Score와 함께 조회")
    @Test
    void readZsetDataWithScoresFromRedis() {
        List<RedisDto> redisDtos = redisUtils.getZSetWithScores(REDIS_MEMBER_SORTED_SET_KEY);
        System.out.println(redisDtos);
    }


    @DisplayName("Zset Paging 조회")
    @Test
    void readZsetRedisDtoPagingWithScores() {
        List<RedisDto> redisDtos = redisUtils.getZSetWithScores(REDIS_MEMBER_SORTED_SET_KEY, 10, 3);
        System.out.println("[RedisDto] size : " + redisDtos.size() +  redisDtos);
    }


    @DisplayName("Zset에서 RANK 조회")
    @Test
    void readZsetDataWithRank() throws JsonProcessingException {
        List<Member> findMemberList = memberRepository.findAll();
        MemberDto memberDto = findMemberList.get(1).getMemberDto();
        Long rank = redisUtils.getZSetWithRank(REDIS_MEMBER_SORTED_SET_KEY, memberDto.getId());
        System.out.println("MEMBER ID : " + memberDto.getId() + "RANK : " + rank);
    }

    @DisplayName("Zset에서 모든 목록 조회")
    @Test
    void readZsetDataAll() {
        Set<Object> zsetDataAll = redisUtils.getZSetValueAll(REDIS_MEMBER_SORTED_SET_KEY);
        zsetDataAll.forEach(System.out::println);
    }

    @DisplayName("Zset에서 리밋 조회")
    @Test
    void readZsetDataLimit() {
        Set<Object> zsetDataLimit = redisUtils.getZSetValueLimit(REDIS_MEMBER_SORTED_SET_KEY, 10L);
        zsetDataLimit.forEach(System.out::println);
    }


    @DisplayName("String KEY로 저장된 목록 조회 하기")
    @Test
    void readRedis() throws JsonProcessingException {
        List<Member> findMemberList = memberRepository.findAll();
        for (Member member : findMemberList) {
            MemberDto dto = redisUtils.getRedisValue(RedisKey.MEMBER.getKey(member.getId()), MemberDto.class);
        }
    }

}
