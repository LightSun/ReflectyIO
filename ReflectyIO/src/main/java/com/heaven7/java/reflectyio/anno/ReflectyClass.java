package com.heaven7.java.reflectyio.anno;

import com.heaven7.java.reflecty.iota.TypeAdapter;

import java.lang.annotation.*;


@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyClass {

    Class<? extends TypeAdapter> value();
}
