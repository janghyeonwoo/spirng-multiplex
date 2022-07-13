package com.example.multiplex.controller;

import com.example.multiplex.entity.Board;
import com.example.multiplex.func.FileFunc;
import com.example.multiplex.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("board")
@RestController
public class BoardController {

    private final BoardService boardService;
    private final FileFunc fileFunc;

    @PostMapping("/board")
    public ResponseEntity<?> createBoard(
            @RequestParam("user") String user,
            @RequestParam("content") String content,
            @RequestParam("files") List<MultipartFile> files
    ) throws Exception {
        Board board = boardService.addBoard(Board.builder()
                .user(user)
                .content(content)
                .build(), files);

        URI uriLocation = new URI("/board/" + board.getBoardIdx());
        return ResponseEntity.created(uriLocation).body("{}");
    }



    @GetMapping("/download")
	public void download(HttpServletResponse response, @RequestParam("boardIdx") Integer boardIdx) throws IOException {
            boardService.fileDown(response,boardIdx);
	}
}
