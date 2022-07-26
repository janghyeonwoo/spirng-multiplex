package com.example.multiplex.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.parameters.P;

@Getter
@AllArgsConstructor
public enum  RoleType {
    USER("일반사용자"),
    ADMIN("관리자");
    private String roleType;
}
