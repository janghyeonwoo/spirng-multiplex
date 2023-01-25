package com.example.multiplex.controller;

import com.example.multiplex.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService productService;


    @GetMapping("list")
    public Object searchProduct(){
        return productService.findProductAll();
    }


}
