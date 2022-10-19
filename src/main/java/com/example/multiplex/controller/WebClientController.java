package com.example.multiplex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class WebClientController {
    private final WebClient webClient;

    @GetMapping("/test")
    public String doTest(HttpServletRequest request , HttpServletResponse response) {
        Map<String, String> req = new HashMap<>();
        req.put("pdBarcode", "9999911620492");


        String result = webClient.post()
                .uri("/api/update/traking")
                .accept(MediaType.ALL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, errRes -> errRes.createException().flatMap(res -> Mono.error(RuntimeException::new)))
                .onStatus(HttpStatus::is4xxClientError, errRes -> errRes.createException().flatMap(res -> Mono.error(RuntimeException::new)))
                .bodyToMono(String.class)
                .block();
        System.out.println("result ::: " + result);
        return result;
    }
}
