package com.example.multiplex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class WebClientController {
    private final WebClient webClient;

    @GetMapping("/test")
    public Mono<String> doTest(HttpServletRequest request , HttpServletResponse response) {
        webClient.get()
                .uri("/board/test")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, errRes -> errRes.createException().flatMap(res -> Mono.error(RuntimeException::new)))
                .onStatus(HttpStatus::is4xxClientError, errRes -> errRes.createException().flatMap(res -> Mono.error(RuntimeException::new)))
                .bodyToMono(String.class)
                .block();

        return new Mono<String>() {
            @Override
            public void subscribe(CoreSubscriber<? super String> actual) {

            }
        };
    }
}
