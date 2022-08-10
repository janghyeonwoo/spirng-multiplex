package com.example.multiplex.controller;

import com.example.multiplex.mapper.EnumMapper;
import com.example.multiplex.type.EnumMapperValue;
import com.example.multiplex.type.FeeType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("enum")
@RestController
public class EnumController {

    private final EnumMapper enumMapper;


    @GetMapping("no-bean-list")
    public List<EnumMapperValue> getNoBeanEnumList() {
        return Arrays.stream(FeeType.values())
                .map(EnumMapperValue::new)
                .collect(Collectors.toList());
    }

    @GetMapping("list")
    public List<EnumMapperValue> getEnumList(){
        return enumMapper.get("FeeType");
    }

    @GetMapping("list-all")
    public Map<String, List<EnumMapperValue>> getEnumAllList(){
        List<String> list = Arrays.asList("FeeType","CodeType");
        return enumMapper.get(list);
    }
}
