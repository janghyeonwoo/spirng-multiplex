package com.example.multiplex.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;


    //deprecated 됨....
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web)-> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     *
     * @see "https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter"
     */
    //websecurityconfigureradapter가 depreacted됨에 따라 filter를 bean으로 등록하여 사용하도록 변경됨
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic();


        http.formLogin().disable();

        http
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/admin/login").permitAll()
                .anyRequest().permitAll()


                .and()
                .addFilterBefore(customAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public CustomAuthFilter customAuthFilter(){
        return new CustomAuthFilter(authenticationManager(), customSucessHandler());
    }

    @Bean
    public CustomSucessHandler customSucessHandler(){
        return new CustomSucessHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(customAuthProvider());
    }

    @Bean
    public CustomAuthProvider customAuthProvider(){
        return new CustomAuthProvider(userDetailsService);
    }

    //패스워드 인코딩
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
