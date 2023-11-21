package com.example.multiplex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
public class MultiplexApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiplexApplication.class, args);
    }

}
