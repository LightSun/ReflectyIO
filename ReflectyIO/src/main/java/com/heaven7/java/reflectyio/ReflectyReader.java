package com.heaven7.java.reflectyio;

public interface ReflectyReader {

    String nextString();

    String nextName();

    void beginArray();

    void endArray();

    boolean hasNext();

    void beginObject(Class<?> clazz);//class can be self-object class and any map class

    void endObject();

    void skipValue();
}
