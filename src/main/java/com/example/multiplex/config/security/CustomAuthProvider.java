package com.example.multiplex.config.security;

import com.example.multiplex.dto.UserDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Log4j2
public class CustomAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String password = (String) token.getCredentials();

        UserDetailDto userDetailDto = (UserDetailDto) userDetailsService.loadUserByUsername(userEmail);

        if(!bCryptPasswordEncoder.matches(password, userDetailDto.getPassword())){
            throw new RuntimeException(userDetailDto.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetailDto, password, userDetailDto.getAuthorities());




    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
