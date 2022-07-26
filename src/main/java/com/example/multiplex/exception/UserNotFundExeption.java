package com.example.multiplex.exception;

public class UserNotFundExeption extends RuntimeException{
    public UserNotFundExeption(String message) {
        super(message + "UserNotFundExeption");
    }
}
