package com.example.multiplex.service;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.MemberEventDto;
import com.example.multiplex.entity.Member;
import com.example.multiplex.redis.RedisRepositoryImpl;
import com.example.multiplex.redisdata.MemberRedis;
import com.example.multiplex.redisdata.MemberRedisRepo;
import com.example.multiplex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.YearMonth;

@RequiredArgsConstructor
@Service
public class MemberService {
//    private final MemberRedisRepository memberRedisRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final RedisRepositoryImpl<String,MemberDto> redisRepository;

    private final MemberRedisRepo memberRedisRepo;

    public void addMember(String name){
        Member member = Member.builder()
                .id(name)
                .name(name)
                .age(23)
                .rgdt(YearMonth.now())
                .build();
        memberRepository.save(member);
//        memberRedisRepository.save(member.getMemberDto());
    }

    public MemberDto findMember(String id) {
        Member member = memberRepository.findById(id).orElse(null);
        return member.getMemberDto();
    }


    public MemberDto updateMember(MemberDto memberDto){
        Member findMember = memberRepository.findById(memberDto.getId()).orElse(null);
        findMember.setName(memberDto.getName());
        findMember.setAge(memberDto.getAge());
        return findMember.getMemberDto();
    }

    public void sendMemberMessage(MemberEventDto memberEventDto){
        publisher.publishEvent(memberEventDto);
    }

    @Transactional
    public void transactionMember(){
        String name = "trans_name";
        int age = 10;

        MemberDto memberDto  = MemberDto.builder().name(name).age(age).build();
        redisRepository.save("trans:10", memberDto);

        Member trans = Member.builder().age(99).id("trans").build();
        memberRepository.save(trans);

        if(true) throw new RuntimeException("트랜잭션 테스트 에러 발생!!!");
    }


//    @Transactional
    public void transactionRedisMember(){
        String name = "trans_name";
        int age = 10;

        MemberRedis memberDto = MemberRedis.builder().id(name).age(age).build();
        memberRedisRepo.save(memberDto);

//        Member trans = Member.builder().age(99).id("trans").build();
//        memberRepository.save(trans);
//
//        if(true) throw new RuntimeException("트랜잭션 테스트 에러 발생!!!");
    }
}
