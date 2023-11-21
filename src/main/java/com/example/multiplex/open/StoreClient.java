package com.example.multiplex.open;

import com.example.multiplex.dto.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "StoreClient", url = "http://localhost/stroe", fallbackFactory  = StoreClient.TestFallbackFactory.class)
public interface StoreClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String getHello();

    @RequestMapping(method = RequestMethod.GET, value = "/hellonotfound")
    String getException();

    @RequestMapping(method = RequestMethod.POST, value = "/hi")
    String getHi(RequestDto requestDto);


    @Slf4j
    @Component
    static class TestFallbackFactory implements FallbackFactory<StoreClient> {
        @Override
        public Fallback create(Throwable cause) {
            log.info("because :: ", cause);
            return new Fallback();
        }

    }


    @Component
    static class Fallback implements StoreClient {

        @Override
        public String getHello() {
            return "hello";
        }

        @Override
        public String getException() {
            return "exception";
        }

        @Override
        public String getHi(RequestDto requestDto) {
            return "hi";
        }
    }


}

