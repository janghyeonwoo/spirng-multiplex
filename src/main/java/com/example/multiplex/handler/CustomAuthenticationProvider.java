package com.example.multiplex.handler;

import com.example.multiplex.dto.UserDetailDto;
import com.example.multiplex.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userId = (String) token.getPrincipal();
        String password = (String) token.getCredentials();

        UserDetailDto userDetailDto =  (UserDetailDto) userDetailsService.loadUserByUsername(userId);
        if(!userDetailDto.getPassword().equals(password)){
            throw new BadCredentialsException(userDetailDto.getUsername() + "Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetailDto, password, userDetailDto.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
