package com.example.multiplex.anno;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageDefault {

    int size() default -1;

    int page() default 1;

    String sortName() default "";

    String sortType() default "";
}
