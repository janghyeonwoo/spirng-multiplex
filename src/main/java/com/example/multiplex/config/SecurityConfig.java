package com.example.multiplex.config;

import com.example.multiplex.filter.CustomAuthenticationFilter;
import com.example.multiplex.handler.CustomAuthenticationProvider;
import com.example.multiplex.handler.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout()
                .logoutSuccessUrl("/logout")
                .logoutSuccessHandler(((request, response, authentication) -> {
//                      try {
//                          String na = "{'aa', : 'aa'}";
//                        response.getWriter().write(na);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }))
                .addLogoutHandler((request, response, authentication) -> {
                    log.info("logout::::::::: start");
                    HttpSession session = request.getSession();
                    session.invalidate();
                    String na = "{'aa', : 'aa'}";
                    try {
                        response.getWriter().write(na);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
     */



    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/member/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customSuccessHandler());
        return customAuthenticationFilter;
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler(){
        return new CustomSuccessHandler();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(userDetailsService,bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth.authenticationProvider(customAuthenticationProvider()));
    }
}
