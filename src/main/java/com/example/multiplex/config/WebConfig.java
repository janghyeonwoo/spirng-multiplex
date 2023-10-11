package com.example.multiplex.config;

import com.example.multiplex.resolver.LoginResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final LoginResolver loginResolver;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(loginResolver);
    }
}
