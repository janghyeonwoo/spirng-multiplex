package com.example.multiplex.service;

import com.example.multiplex.entity.Board;
import com.example.multiplex.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestService {

    private final BoardRepository boardRepository;
    public Board getBoard(Integer id){
        System.out.println("실행됨???");
        return boardRepository.findById(id).get();
    }
}
