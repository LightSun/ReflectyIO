package com.heaven7.java.reflectyio.yaml;

public interface ParentTypeWriter {
    void name(String s);

    void nullValue();

    void value(Number obj);

    void value(Boolean obj);

    void value(Character chz);

    void value(String str);
}