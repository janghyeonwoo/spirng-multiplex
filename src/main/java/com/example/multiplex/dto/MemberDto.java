package com.example.multiplex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@ToString
@Builder
@Getter
public class MemberDto {
    private String id;
    private String name;
}
