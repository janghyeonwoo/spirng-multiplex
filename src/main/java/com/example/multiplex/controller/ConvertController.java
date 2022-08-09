package com.example.multiplex.controller;

import com.example.multiplex.entity.Code;
import com.example.multiplex.repository.CodeRepository;
import com.example.multiplex.type.AdStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("convert")
public class ConvertController {
    private final CodeRepository codeRepository;

    @GetMapping("/save")
    public String saveCode(){
        Code codeEnt = Code.builder().adStatus(null)
                .build();
        codeRepository.save(codeEnt);
        return "success";
    }

    @GetMapping("/find")
    public String findCode(){
        List<Code> all = codeRepository.findAll();
        all.forEach(System.out::println);
        return "success";
    }

}
