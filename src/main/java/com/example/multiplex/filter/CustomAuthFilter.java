package com.example.multiplex.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userId, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
    }
}
