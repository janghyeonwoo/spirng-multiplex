package com.example.multiplex.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public abstract class AbstractCustomException extends RuntimeException {
    public final ErrorCode errorCode;
    public final String errorMessage;


    public AbstractCustomException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public AbstractCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }



}
