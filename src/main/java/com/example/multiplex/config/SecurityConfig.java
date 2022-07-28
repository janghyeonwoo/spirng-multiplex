package com.example.multiplex.config;

import com.example.multiplex.filter.CustomAuthFilter;
import com.example.multiplex.handler.CustomAuthProvider;
import com.example.multiplex.handler.CustomAuthSuccessHandler;
import com.example.multiplex.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()
                .antMatchers("/board/**").hasAnyAuthority(RoleType.USER.getValue())
                .anyRequest().permitAll()
                .and()
                .logout().logoutUrl("/member/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/member/logouts")
                .and()
                .addFilterBefore(customAuthFilter(),UsernamePasswordAuthenticationFilter.class);
    }




    public CustomAuthFilter customAuthFilter() throws Exception {
        CustomAuthFilter customAuthFilter = new CustomAuthFilter(authenticationManagerBean());
        customAuthFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler());
        customAuthFilter.setUsernameParameter("userId");
        customAuthFilter.setFilterProcessesUrl("/member/login");
        return customAuthFilter;
    }

    @Bean
    public CustomAuthSuccessHandler customAuthSuccessHandler(){
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public CustomAuthProvider customAuthProvider(){
        return new CustomAuthProvider(userDetailsService);
    }


    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider());
    }


}
