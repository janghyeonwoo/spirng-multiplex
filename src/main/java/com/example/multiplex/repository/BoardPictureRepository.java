package com.example.multiplex.repository;

import com.example.multiplex.entity.BoardPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardPictureRepository extends JpaRepository<BoardPicture,Integer> {
    List<BoardPicture> findByBoardIdxOrderByID(@Param("boardIdx") Integer boardIdx);
}
