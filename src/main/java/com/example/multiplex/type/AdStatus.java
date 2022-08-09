package com.example.multiplex.type;

import lombok.Getter;

import java.util.EnumSet;

@Getter
public enum AdStatus implements LegacyCommonType{
    READY("대기", "1"),
    DOING("진행중", "2"),
    END("종료", "3");

    private String desc;
    private String legacyCode;

    AdStatus(String desc, String legacyCode) {
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

//    public static AdStatus ofLegacyCode(String legacyCode) {
//        return EnumSet.allOf(AdStatus.class).stream()
//                .filter(v -> v.getLegacyCode().equals(legacyCode))
//                .findAny()
//                .orElseThrow(RuntimeException::new);
//    }
}

