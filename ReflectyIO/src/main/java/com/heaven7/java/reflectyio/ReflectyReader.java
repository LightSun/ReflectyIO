package com.heaven7.java.reflectyio;

public interface ReflectyReader {

    String nextString();

    String nextName();

    void beginArray();

    void endArray();

    boolean hasNext();

    void beginObject();

    void endObject();

    void skipValue();
}
