package com.example.multiplex.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema
public class Page {
    @Schema(description = "페이지 번호(0..N)")
    private Integer page;

    @Schema(description = "페이지 크기")

    private Integer size;

    @Schema(description = "정렬(사용법: 컬럼명,ASC|DESC)")
    private List<String> sort;
}
