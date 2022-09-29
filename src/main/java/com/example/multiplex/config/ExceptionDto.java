package com.example.multiplex.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionDto {

    private int code;
    private String message;
}
