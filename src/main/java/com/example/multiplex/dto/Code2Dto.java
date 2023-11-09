package com.example.multiplex.dto;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Code2Dto {

    private Long code1Idx;
    private Long code2Idx;
    private String value;
}
