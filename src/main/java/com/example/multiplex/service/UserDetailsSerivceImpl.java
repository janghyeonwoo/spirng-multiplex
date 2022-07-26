package com.example.multiplex.service;

import com.example.multiplex.dto.UserDetailDto;
import com.example.multiplex.exception.UserNotFundExeption;
import com.example.multiplex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class UserDetailsSerivceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUserId(username)
                .map(member -> new UserDetailDto(member, Collections.singleton(new SimpleGrantedAuthority(member.getRoleType().getRoleType()))))
                .orElseThrow(() -> new UserNotFundExeption(username));
    }
}
