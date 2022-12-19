package com.example.multiplex.dto;

import com.example.multiplex.type.StatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class RequestDto {
    private String name;
    private StatusType statusType;
}
