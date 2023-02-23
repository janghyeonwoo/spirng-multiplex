package com.example.multiplex.config.security;


import com.example.multiplex.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import sun.nio.cs.UTF_8;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomAuthFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/admin/login", "POST");


	public CustomAuthFilter(AuthenticationManager authenticationManager, CustomSucessHandler customSucessHandler) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
		super.setAuthenticationSuccessHandler(customSucessHandler);
	}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(request.getContentType() == null || !request.getContentType().equals(ContentType.APPLICATION_JSON.getMimeType())){
            throw new RuntimeException("Authentication Content-Type not spupported.....!!!");
        }

        LoginDto loginDto = new ObjectMapper().readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), LoginDto.class);

        String id = loginDto.getId();
        String password = loginDto.getPassword();

        if(!StringUtils.hasText(id) || !StringUtils.hasText(password)) {
            throw new RuntimeException("Not Empty id or password !!");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(id,password);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return authRequest;
    }
}
