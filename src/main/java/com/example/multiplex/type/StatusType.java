package com.example.multiplex.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;

@Getter
public enum StatusType {
    DOING(3, "주문진행중"),
    DONE(4, "주문완료");

    private Integer code;
    @JsonValue
    private String msg;

    StatusType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @JsonCreator
    public static StatusType of(Integer statusType) {
        return Arrays.stream(StatusType.values())
                .filter(i -> i.code.equals(statusType))
                .findAny()
                .orElse(null);
    }
}
