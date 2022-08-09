package com.example.multiplex.util;

import com.example.multiplex.type.LegacyCommonType;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

@NoArgsConstructor
public class LegacyEnumConvertUtils {
    public static <T extends Enum<T> & LegacyCommonType> T ofLegacyCode(Class<T> enumClass, String legacyCode) {
        if (StringUtils.isEmpty(legacyCode)) {
            return null;
        }
        return EnumSet.allOf(enumClass).stream()
                .filter(v -> v.getLegacyCode().equals(legacyCode))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    public static <T extends Enum<T> & LegacyCommonType> String toLegacyCode(T enumValue){
        if(enumValue == null){
            return "";
        }
        return enumValue.getLegacyCode();
    }

}
