package com.heaven7.java.reflectyio.anno;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyMethodType {

    byte TYPE_GET = 1;
    byte TYPE_SET = 2;

    byte value() default TYPE_SET;
}
