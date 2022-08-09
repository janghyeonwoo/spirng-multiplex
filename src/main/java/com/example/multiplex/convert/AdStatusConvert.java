package com.example.multiplex.convert;

import com.example.multiplex.type.AdStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;


@Convert
public class AdStatusConvert extends AbstractLegacyEnumConvert<AdStatus> {
    private static final String ENUM_NAME = "광고상태";

    public AdStatusConvert() {
        super(false, ENUM_NAME, AdStatus.class);
    }
}
