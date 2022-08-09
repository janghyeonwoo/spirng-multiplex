package com.example.multiplex.convert;

import com.example.multiplex.type.LegacyCommonType;
import com.example.multiplex.util.LegacyEnumConvertUtils;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

@Getter
public class AbstractLegacyEnumConvert<E extends Enum<E> & LegacyCommonType> implements AttributeConverter<E, String> {
    private Class<E> targetEnumClass;
    private boolean nullable;
    private String enumName;

    public AbstractLegacyEnumConvert(boolean nullable, String enumName, Class<E> targetEnumClass) {
        this.nullable = nullable;
        this.enumName = enumName;
        this.targetEnumClass = targetEnumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        System.out.println("==================== convertToDatabaseColumn");
        if (!nullable && attribute == null) {
            throw new IllegalArgumentException(String.format("%s(은)는 NULL로 저장할수 없습니다", enumName));
        }
        return LegacyEnumConvertUtils.toLegacyCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (!nullable && StringUtils.isEmpty(dbData)) {
            throw new IllegalArgumentException(String.format("%s(은)는 DB에 NULL 혹은 Empty로(%s) 저장되어 있습니다", enumName, dbData));
        }
        return LegacyEnumConvertUtils.ofLegacyCode(targetEnumClass, dbData);
    }
}
