package com.example.multiplex.exception;

import lombok.Getter;

@Getter
public class ClientException extends AbstractCustomException {

    public ClientException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ClientException(String errorMessage) {
        super(ErrorCode.BAD_REQUEST, errorMessage);
    }
}
