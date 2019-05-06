package com.heaven7.java.reflectyio.anno;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyInherit {

    boolean value() default true;
}
