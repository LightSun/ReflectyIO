package com.heaven7.java.reflectyio.anno;

import java.lang.annotation.*;


@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyField {

    String property();

}
