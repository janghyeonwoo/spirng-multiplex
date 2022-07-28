package com.example.multiplex.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.text.html.Option;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        log.info("CustomAuthProvider userId : {}", userId);
        log.info("CustomAuthProvider password : {}", password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        if(!userDetails.getPassword().equals(password)){
            throw new RuntimeException("일치하지 않는 비밀번호입니다.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
