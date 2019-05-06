package com.heaven7.java.reflectyio.anno;

import java.lang.annotation.*;


@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyMethod {

    String value() default "";
}
