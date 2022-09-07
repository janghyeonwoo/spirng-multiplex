package com.example.multiplex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqDto {
    @Schema(name = "name", description = "이름입니다")
    private String name;
    private String age;
    @Schema(name = "type", description = "타입입니다" , required = true)
    private int type;
}
