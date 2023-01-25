package com.example.multiplex.predi;

import com.example.multiplex.ProductDto;

public class ProductPricePredicate implements ProductPredicate{
    @Override
    public boolean test(ProductDto productDto, ProductDto compare) {
        return productDto.getPrice().equals(compare.getPrice());
    }
}
