package com.example.multiplex.controller;

import com.example.multiplex.anno.PageDefault;
import com.example.multiplex.dto.PageRequest;
import com.example.multiplex.entity.Board;
import com.example.multiplex.func.FileFunc;
import com.example.multiplex.service.BoardService;
import com.example.multiplex.type.BoardOrderType;
import com.example.multiplex.type.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
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

    @GetMapping
    public ResponseEntity<?> findBoards(@PageDefault(size = 10, sortName = "SEQ", sortType = "ASC")PageRequest<BoardOrderType, SortType> pageRequest){
        log.info("Page +++ {}", pageRequest);
        return ResponseEntity.ok("SUCCESS");

    }



    @GetMapping("/download")
	public void download(HttpServletResponse response, @RequestParam("boardIdx") Integer boardIdx) throws IOException {
            boardService.fileDown(response,boardIdx);
	}
}
