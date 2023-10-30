package com.example.multiplex.service;

import com.example.multiplex.dto.MemberDto;
import com.example.multiplex.dto.MemberEventDto;
import com.example.multiplex.entity.Member;
import com.example.multiplex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@RequiredArgsConstructor
@Service
public class MemberService {
//    private final MemberRedisRepository memberRedisRepository;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

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
}
