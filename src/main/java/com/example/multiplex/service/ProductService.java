package com.example.multiplex.service;

import com.example.multiplex.ProductDto;
import com.example.multiplex.predi.ProductPredicate;
import com.example.multiplex.predi.ProductPricePredicate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {


    public List<ProductDto> findProductAll(){
        List<ProductDto> productDtoList = initProduct();
        ProductDto productDto = ProductDto.builder().price(5).name("product5").build();
        return filterProductDto(productDtoList,new ProductPricePredicate(),productDto);
    }

    /**
     *
     * @param productDtoList
     * @param predicate
     * @param compare
     * https://github.com/jojoldu/blog-code/blob/master/java8-in-action/src/main/java/action/chap2/FruitInventory.java
     * 별도 클래스로 빼고 유틸성으로 만들어야한다...
     */
    private List<ProductDto> filterProductDto(List<ProductDto> productDtoList, ProductPredicate predicate, ProductDto compare){
        List<ProductDto> productDtos = new ArrayList<>();
        for(ProductDto productDto : productDtoList){
            if(predicate.test(productDto,compare)){
                productDtos.add(productDto);
            }
        }
        return productDtos;
    }

    private List<ProductDto> initProduct(){
        return IntStream.rangeClosed(1,10)
                .mapToObj(i -> {
                    return ProductDto.builder().name("product" + i).price(i).build(); })
                .collect(Collectors.toList());
    }
}
