package com.heaven7.java.reflectyio;

public interface ReflectyWriter {

    void nullValue();

    void value(Number obj);

    void value(Boolean obj);

    void name(String s);

    void value(Character chz);

    void value(String str);

    void beginArray();

    void endArray();

    void beginObject();

    void endObject();
}
