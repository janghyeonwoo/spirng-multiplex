package com.example.multiplex.config;

import feign.*;
import org.bouncycastle.util.Arrays;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients("com.example.multiplex.open")
public class OpenFeignConfig {


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (Arrays.isNullOrEmpty(requestTemplate.body()) && !isGetOrDelete(requestTemplate)) {
                // body가 비어있는 경우에 요청을 보내면 411 에러가 생김 https://github.com/OpenFeign/feign/issues/1251
                // content-length로 처리가 안되어서 빈 값을 항상 보내주도록 함
                requestTemplate.body("{}");
            }
        };
    }

    private boolean isGetOrDelete(RequestTemplate requestTemplate) {
        return Request.HttpMethod.GET.name().equals(requestTemplate.method())
                || Request.HttpMethod.DELETE.name().equals(requestTemplate.method());
    }


//    @Bean
//    Retryer.Default retryer() {
//        // 0.1초의 간격으로 시작해 최대 3초의 간격으로 점점 증가하며, 최대5번 재시도한다.
//        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(3L), 2);
//    }

}
