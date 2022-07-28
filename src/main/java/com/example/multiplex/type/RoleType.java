package com.example.multiplex.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleType {
    USER("일반사용자"),ADMIN("관리자");
    private final String value;
}
