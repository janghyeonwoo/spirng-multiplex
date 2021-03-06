package com.example.multiplex.service;

import com.example.multiplex.entity.Board;
import com.example.multiplex.entity.BoardPicture;
import com.example.multiplex.func.FileFunc;
import com.example.multiplex.repository.BoardPictureRepository;
import com.example.multiplex.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardPictureRepository boardPictureRepository;
    private final BoardRepository boardRepository;
    private final FileFunc fileFunc;

    public Board addBoard(Board board, List<MultipartFile> files) throws Exception {
        Board saveBoard = boardRepository.save(board);
        // 파일을 저장하고 그 BoardPicture 에 대한 list 를 가지고 있는다
        List<BoardPicture> list = fileFunc.parseFileInfo(saveBoard.getBoardIdx(), files);
        if(list.isEmpty()){
            // TODO : 파일이 없을 땐 어떻게 해야할까.. 고민을 해보아야 할 것
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
            List<BoardPicture> pictureBeans = new ArrayList<>();
            for(BoardPicture boardPicture : list) {
                pictureBeans.add(boardPictureRepository.save(boardPicture));
            }
        }
        return boardRepository.save(board);
    }


    public void fileDown(HttpServletResponse response, Integer boardIdx) throws IOException {
        List<BoardPicture> boardPicture = boardPictureRepository.findByBoardIdxOrderByID(boardIdx);
        BoardPicture firstBoardPicture = boardPicture.get(0);

        Path path = Paths.get(firstBoardPicture.getStoredFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path)); // 파일 resource 얻기

        File file = new File(path.toUri());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentLength((int) file.length());        //--- response content length를 설정합니다.
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));    //--- inputstream 객체를 얻습니다.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
