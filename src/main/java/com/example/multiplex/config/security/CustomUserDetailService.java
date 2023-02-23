package com.example.multiplex.config.security;

import com.example.multiplex.dto.UserDetailDto;
import com.example.multiplex.entity.User;
import com.example.multiplex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).map(i -> new UserDetailDto(i, Collections.singleton(new SimpleGrantedAuthority("ADMIN")))).orElseThrow(() -> new RuntimeException("없는 회원입니다"));

    }
}
