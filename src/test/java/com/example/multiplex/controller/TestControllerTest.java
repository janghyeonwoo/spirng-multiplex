package com.example.multiplex.controller;

import com.example.multiplex.entity.Board;
import com.example.multiplex.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    void getTest() throws Exception {
        BDDMockito.given(testService.getBoard(1)).willReturn(new Board(1,"111","1112"));
        mockMvc.perform(MockMvcRequestBuilders.get("/test/getTest").param("boardIdx","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.boardIdx").exists())
                .andDo(MockMvcResultHandlers.print());
    }
}
