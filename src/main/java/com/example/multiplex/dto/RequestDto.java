package com.example.multiplex.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestDto {
    private String name;
    private Integer age;
}
