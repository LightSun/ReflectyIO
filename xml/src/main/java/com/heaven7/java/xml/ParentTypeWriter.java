package com.heaven7.java.xml;

import java.io.IOException;

public interface ParentTypeWriter {

    void nullValue(XmlWriterImpl impl, String lastName) throws IOException;

    void value(XmlWriterImpl impl, String lastName, Number obj) throws IOException;

    void value(XmlWriterImpl impl, String lastName, Boolean obj) throws IOException;

    void value(XmlWriterImpl impl, String lastName, Character obj) throws IOException;

    void value(XmlWriterImpl impl, String lastName, String obj) throws IOException;

    void endArray(XmlWriterImpl impl, String lastName) throws IOException;

    void endObject(XmlWriterImpl impl, String lastName) throws IOException;

}
