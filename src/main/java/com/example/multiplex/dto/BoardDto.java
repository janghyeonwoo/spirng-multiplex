package com.example.multiplex.dto;

import com.example.multiplex.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BoardDto {
    private Integer boardIdx;
    private String user;
    private String content;


    public BoardDto(Board board) {
        this.boardIdx = board.getBoardIdx();
        this.user = board.getUser();
        this.content = board.getContent();
    }

    public Board toEntity(){
        return Board.builder()
                .user(this.user)
                .content(this.content)
                .build();
    }
}
