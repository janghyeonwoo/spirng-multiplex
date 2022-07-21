package com.example.multiplex.type;

import com.querydsl.core.types.OrderSpecifier;

public interface ISortType {
    OrderSpecifier getOrder(ISortName sortName);

}
