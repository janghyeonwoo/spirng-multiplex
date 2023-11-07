package com.example.multiplex.exception;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ErrorResponse {
    @Builder.Default
    private final boolean result = false;
    public int resultCode;
    private String resultMessage;


    public static ErrorResponse fail(){
        return ErrorResponse.builder().resultCode(200).build();
    }

    public static ErrorResponse fail(int resultCode, String resultMessage){
        return ErrorResponse.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }
}
