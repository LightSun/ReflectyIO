package com.heaven7.java.xml;

import java.io.IOException;

public class ObjectTypeWriter implements ParentTypeWriter {

    @Override
    public void nullValue(XmlWriterImpl impl, String lastName) throws IOException {
        impl.attribute(lastName, null);
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Number obj) throws IOException {
        impl.attribute(lastName, obj);
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Boolean obj) throws IOException {
        impl.attribute(lastName, obj);
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Character obj) throws IOException {
        impl.attribute(lastName, obj);
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, String obj) throws IOException {
        impl.attribute(lastName, obj);
    }

    @Override
    public void endArray(XmlWriterImpl impl, String lastName) throws IOException {

    }

    @Override
    public void endObject(XmlWriterImpl impl, String lastName) throws IOException {
        impl.pop();
    }
}
