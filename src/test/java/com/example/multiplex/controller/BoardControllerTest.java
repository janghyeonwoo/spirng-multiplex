package com.example.multiplex.controller;

import com.example.multiplex.dto.BoardDto;
import com.example.multiplex.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MockMvc mockMvc;


    void setUpBoard() {
        IntStream.rangeClosed(1, 3).forEach(i -> {
            BoardDto boardDto = BoardDto.builder()
                    .content("게시판 등록 테스트 게시판" + i)
                    .user("테스트 유저")
                    .build();

            boardService.createBoard(boardDto);
        });
    }

    @DisplayName("게시판 상세 조회")
    @Test
    void get_board() throws Exception {
        //when
        String boardIdx = "1";

        //when
        MockHttpServletRequestBuilder MMR = MockMvcRequestBuilders.get("/board/{boardIdx}",boardIdx)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(MMR)
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardIdx").value(boardIdx))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists());
    }

    @DisplayName("게시판 등록")
    @Test
    void create_board() throws Exception {
        BoardDto boardDto = BoardDto.builder()
                .content("게시판 등록 테스트 게시판")
                .user("테스트 유저")
                .build();


        MockHttpServletRequestBuilder MMR = MockMvcRequestBuilders.post("/board")
                .content(new ObjectMapper().writeValueAsString(boardDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(MMR)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Rollback
    @DisplayName("게시판 목록 조회")
    @Test
    void board_list() throws Exception {
        setUpBoard();
        MockHttpServletRequestBuilder MMR = MockMvcRequestBuilders.get("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(MMR)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.",Matchers.hasSize(2)))
                .andDo(MockMvcResultHandlers.print());
    }




}
