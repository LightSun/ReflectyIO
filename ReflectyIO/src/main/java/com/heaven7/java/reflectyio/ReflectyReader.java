package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.ReflectyContext;

public interface ReflectyReader {

    String nextString();

    String nextName();

    void beginArray();

    void endArray();

    boolean hasNext();

    void beginObject(ReflectyContext context, Class<?> clazz);//class can be self-object class and any map class

    void endObject();

    void skipValue();
}
