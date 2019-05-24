package com.heaven7.java.reflectyio.json;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.IOException;
import java.io.Reader;

public final class JsonReader implements ReflectyReader {

    private final com.google.gson.stream.JsonReader reader;

    public JsonReader(Reader reader) {
        this.reader = new com.google.gson.stream.JsonReader(reader);
    }

    @Override
    public void begin() throws IOException {

    }

    @Override
    public void end() throws IOException {
    }

    @Override
    public String nextString() throws IOException {
        return reader.nextString();
    }

    @Override
    public boolean nextBoolean() throws IOException {
        return reader.nextBoolean();
    }

    @Override
    public String nextName() throws IOException {
        return reader.nextName();
    }

    @Override
    public void beginArray() throws IOException {
        reader.beginArray();
    }

    @Override
    public void endArray() throws IOException {
        reader.endArray();
    }

    @Override
    public boolean hasNext() throws IOException {
        return reader.hasNext();
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) throws IOException {
        reader.beginObject();
    }

    @Override
    public void endObject() throws IOException {
        reader.endObject();
    }

    @Override
    public void skipValue() throws IOException {
        reader.skipValue();
    }
}

