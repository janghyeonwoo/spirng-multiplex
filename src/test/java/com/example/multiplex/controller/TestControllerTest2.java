package com.example.multiplex.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest2 {
    @Autowired
    private MockMvc mockMvc;



    @DisplayName("게시판 상세 조회")
    @Test
    void get_test() throws Exception {
        //when
        Integer boardIdx = 1;

        //when
        MockHttpServletRequestBuilder MMR = MockMvcRequestBuilders.get("/test/getTest").param("boardIdx", String.valueOf(boardIdx))
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(MMR)
                .andDo(MockMvcResultHandlers.print());

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardIdx").value(boardIdx))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user").exists());

    }
}
