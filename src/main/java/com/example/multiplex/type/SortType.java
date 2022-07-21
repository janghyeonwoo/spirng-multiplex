package com.example.multiplex.type;

import com.querydsl.core.types.OrderSpecifier;

public enum SortType implements  ISortType{
    ASC, DESC;


    @Override
    public OrderSpecifier getOrder(ISortName sortName) {
        OrderSpecifier result;
        switch (this) {
            case ASC:
                result = sortName.getExpression().asc();
                break;

            case DESC:
                result = sortName.getExpression().desc();
                break;
            default:
                throw new IllegalStateException("Unexpeted value : " + this);
        }
        return result;
    }

    public Enum<SortType> value(String value) { return SortType.valueOf(value);}
}
