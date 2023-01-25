package com.example.multiplex.predi;

import com.example.multiplex.ProductDto;

public class ProductNamePredicate implements ProductPredicate{
    @Override
    public boolean test(ProductDto productDto, ProductDto compare) {
        return productDto.getName().equals(compare.getName());
    }
}
