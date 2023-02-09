package com.example.multiplex.config.security;

import com.example.multiplex.dto.UserDetailDto;
import com.example.multiplex.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetailDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).map(i -> new UserDetailDto(i, Collections.singleton(new SimpleGrantedAuthority(i.getRole())))).orElseThrow(() -> new RuntimeException("존재 하지 않는 아이디입니다"));
    }
}

