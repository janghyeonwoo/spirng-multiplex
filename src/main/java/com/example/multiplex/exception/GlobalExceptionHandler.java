package com.example.multiplex.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    ResponseEntity<ErrorResponse> clientException(ClientException e){
        log.error("[ClientException] : ", e);
        ErrorResponse errorResponse = ErrorResponse.fail(e.getErrorCode().getCode(),e.getErrorMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
