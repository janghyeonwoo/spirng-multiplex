package com.example.multiplex.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, "잘못된 요청입니다"),
    NO_AUTH(403, "접근 권한이 없습니다"),
    SERVER_EXCEPTION(500, "서버에서 에러가 발생");
    ;
    private final int code;
    private final String message;
}
