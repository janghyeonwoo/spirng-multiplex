package com.example.multiplex.exception;

import lombok.Getter;

@Getter
public class ServerException extends AbstractCustomException{

    public ServerException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ServerException(String errorMessage) {
        super(ErrorCode.SERVER_EXCEPTION, errorMessage);
    }
}
