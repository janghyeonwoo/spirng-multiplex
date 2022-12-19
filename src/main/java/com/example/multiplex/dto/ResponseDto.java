package com.example.multiplex.dto;


import com.example.multiplex.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@AllArgsConstructor
@Setter
@Getter
public class ResponseDto {
    private String name;
    private StatusType statusType;
}
