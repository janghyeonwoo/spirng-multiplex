package com.example.multiplex.converter;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


/**
 * {@code autoApply=true}는 @Convert 사용 하지 않고  전역 으로 쓰고 자 할 때 사용 한다.
 */
@Converter(autoApply = false)
public class YearMonthConverter implements AttributeConverter<YearMonth, String> {
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return ObjectUtils.isEmpty(attribute) ? null : attribute.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData)? YearMonth.parse(dbData,DateTimeFormatter.ofPattern("yyyy-MM")) : null ;
    }
}
