package com.example.multiplex.predi;

import com.example.multiplex.ProductDto;

public interface ProductPredicate {
    boolean test(ProductDto productDto, ProductDto compare);
}
