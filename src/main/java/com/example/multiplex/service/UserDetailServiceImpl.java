package com.example.multiplex.service;

import com.example.multiplex.dto.UserDetailsDto;
import com.example.multiplex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("userId : {}", userId);
        return memberRepository.findByUserId(userId)
                .map(member -> new UserDetailsDto(member, Collections.singleton(new SimpleGrantedAuthority(member.getRoleType().getValue()))))
        .orElseThrow(RuntimeException::new);
    }
}
