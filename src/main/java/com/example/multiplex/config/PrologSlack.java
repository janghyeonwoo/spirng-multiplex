package com.example.multiplex.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class PrologSlack {

    private static final String SLACK_LOGGER_WEBHOOK_URI = "https://hooks.slack.com/services/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";
//        System.getenv("SLACK_LOGGER_WEBHOOK_URI");

    public final ObjectMapper objectMapper;

    public PrologSlack(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void send(String message) {
        WebClient.create(SLACK_LOGGER_WEBHOOK_URI)
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(toJson(message))
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    private String toJson(String message) {
        try {
            Map<String, String> values = new HashMap<>();
            values.put("text", message);

            return objectMapper.writeValueAsString(values);
        } catch (JsonProcessingException ignored) {
        }
        return "{\"text\" : \"슬랙으로 보낼 데이터를 제이슨으로 변경하는데 에러가 발생함.\"}";
    }
}
