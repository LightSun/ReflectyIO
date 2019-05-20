package com.heaven7.java.json;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * the json writer
 * @author heaven7
 */
public class JsonWriter implements ReflectyWriter {

    private final com.google.gson.stream.JsonWriter writer;

    public JsonWriter(Writer writer) {
        this.writer = new com.google.gson.stream.JsonWriter(writer);
        this.writer.setHtmlSafe(true);
    }

    public void begin(Object obj) throws IOException {

    }

    @Override
    public void end(Object obj) throws IOException {
    }

    @Override
    public void name(String s) throws IOException {
        writer.name(s);
    }

    @Override
    public void nullValue() throws IOException {
        writer.nullValue();
    }

    @Override
    public void value(Number obj) throws IOException {
        writer.value(obj);
    }

    @Override
    public void value(Boolean obj) throws IOException {
        writer.value(obj);
    }

    @Override
    public void value(Character obj) throws IOException {
        writer.value(obj);
    }

    @Override
    public void value(String obj) throws IOException {
        writer.value(obj);
    }

    @Override
    public void beginArray() throws IOException {
        writer.beginArray();
    }

    @Override
    public void endArray() throws IOException {
        writer.endArray();
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) throws IOException {
        writer.beginObject();
    }

    @Override
    public void endObject() throws IOException {
        writer.endObject();
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }
}
