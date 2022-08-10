package com.example.multiplex.type;

public enum CodeType implements EnumMapperType{
    STOP("정지"),
    START("진행");

    private String title;

    CodeType(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String title() {
        return title;
    }
}
