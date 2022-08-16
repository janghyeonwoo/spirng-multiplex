package com.example.multiplex.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqDto {
    @ApiModelProperty(name = "name", value = "이름입니다")
    private String name;
}
