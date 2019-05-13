package com.heaven7.java.xml;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;

public final class XmlWriter implements ReflectyWriter {

    private static final String DEFAULT_NAME = "root";
    private static final byte TYPE_OBJECT = 1;
    private static final byte TYPE_ARRAY  = 2;
    //private static final byte TYPE_MAP    = 3;

    private final ArrayTypeWriter mArrayWriter = new ArrayTypeWriter();
    private final ObjectTypeWriter mObjWriter  = new ObjectTypeWriter();

    private final XmlWriterImpl impl;
    private ParentTypeWriter pWriter;
    private byte parentType;
    private String name;

    public XmlWriter(Writer writer) {
        this.impl = new XmlWriterImpl(writer);
    }

    @Override
    public void begin(Object obj) throws IOException {
        final String name;
        XmlRoot root = obj.getClass().getAnnotation(XmlRoot.class);
        if(root != null){
            name = root.value();
        }else {
            name = DEFAULT_NAME;
        }
        impl.element(name);
    }

    @Override
    public void end(Object obj) throws IOException{

    }

    @Override
    public void name(String s) throws IOException {
        this.name = s;
    }

    @Override
    public void nullValue() throws IOException {
        pWriter.nullValue(impl, name);
    }

    @Override
    public void value(Number obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(Boolean obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(Character obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(String obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void beginArray() throws IOException {
        parentType = TYPE_ARRAY;
        setParentWriter();
    }

    @Override
    public void endArray() throws IOException {
        pWriter.endArray(impl, name);
        name = null;
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) throws IOException {
        //last is list. and this is map ? add child element ?
        if(parentType == TYPE_OBJECT){
            if(name != null){
                impl.element(name);
                name = null;
            }
        }else {

        }
        parentType = TYPE_OBJECT;
        setParentWriter();
    }

    @Override
    public void endObject() throws IOException {
        pWriter.endObject(impl, name);
        name = null;
    }

    @Override
    public void flush() throws IOException {
        impl.flush();
    }

    public void bodyText(Object value) throws IOException{
        impl.text(value);
    }

    private void setParentWriter() {
        switch (parentType){
            case TYPE_ARRAY:
                pWriter = mArrayWriter;
                break;
            case TYPE_OBJECT:
                pWriter = mObjWriter;
                break;
        }
    }
}
