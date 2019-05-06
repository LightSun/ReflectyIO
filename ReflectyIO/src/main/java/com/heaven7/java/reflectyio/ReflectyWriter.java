package com.heaven7.java.reflectyio;

public interface ReflectyWriter {

    void name(String s);

    void nullValue();

    void value(Number obj);

    void value(Boolean obj);

    void value(Character chz);

    void value(String str);

    void beginArray();

    void endArray();

    void beginObject(Class<?> clazz);//class can be self-object class and any map class

    void endObject();
}
