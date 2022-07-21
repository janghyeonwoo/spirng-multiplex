package com.example.multiplex.type;


import com.example.multiplex.entity.QBoard;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import static com.example.multiplex.entity.QBoard.board;

public enum BoardOrderType implements ISortName{
    IDX(board.boardIdx), NAME(board.user);

    private ComparableExpressionBase expressionBase;

    BoardOrderType(ComparableExpressionBase expressionBase) {
        this.expressionBase = expressionBase;
    }


    @Override
    public ComparableExpressionBase getExpression() {
        return expressionBase;
    }
}
