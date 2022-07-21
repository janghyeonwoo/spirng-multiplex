package com.example.multiplex.dto;

import com.example.multiplex.type.ISortName;
import com.example.multiplex.type.ISortType;
import com.querydsl.core.types.OrderSpecifier;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageRequest <T extends Enum & ISortName, S extends Enum & ISortType> {

    private Integer page;
    private Integer size;
    private T sortName;
    private S sortType;

    @Builder
    public PageRequest(Integer page, Integer size, Enum sortName, Enum sortType) {
        this.page = page;
        this.size = size;
        this.sortName = (T) sortName;
        this.sortType = (S) sortType;
    }

    public Long offset() {
        if (size == -1)
            return null;
        return (long) (page - 1) * size;
    }

    public long pageSize() {
        return size;
    }

    public OrderSpecifier orderSpec() {
        return sortType.getOrder(sortName);
    }
}
