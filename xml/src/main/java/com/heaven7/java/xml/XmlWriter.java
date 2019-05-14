package com.heaven7.java.xml;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

public final class XmlWriter implements ReflectyWriter, IXmlWriter {

    private static final String DEFAULT_NAME = "root";
    private static final byte TYPE_OBJECT = 1;
    private static final byte TYPE_ARRAY  = 2;
    //private static final byte TYPE_MAP    = 3;

    private final ArrayTypeWriter mArrayWriter = new ArrayTypeWriter();
    private final ObjectTypeWriter mObjWriter  = new ObjectTypeWriter();

    private final Stack<Byte> parentTypeStack = new Stack<>();
    private final XmlWriterImpl impl;
    private ParentTypeWriter pWriter;
    private String name;

    public XmlWriter(Writer writer) {
        this.impl = new XmlWriterImpl(writer);
    }

    @Override
    public void begin(Object obj) throws IOException {
        final String name;
        XmlElement root = obj.getClass().getAnnotation(XmlElement.class);
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
        parentTypeStack.push(TYPE_ARRAY);
        setParentWriter();
    }

    @Override
    public void endArray() throws IOException {
        pWriter.endArray(impl, name);
        name = null;
        parentTypeStack.pop();
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) throws IOException {
        //last is list. and this is map ? add child element ?
        if(!parentTypeStack.isEmpty()){
            if(parentTypeStack.peek() == TYPE_OBJECT){
                if(name != null){
                    impl.element(name);
                    name = null;
                }
            }
        }
        parentTypeStack.push(TYPE_OBJECT);
        setParentWriter();
    }

    @Override
    public void endObject() throws IOException {
        pWriter.endObject(impl, name);
        name = null;
        parentTypeStack.pop();
    }

    @Override
    public void flush() throws IOException {
        impl.flush();
    }

    public void bodyText(Object value) throws IOException{
        impl.text(value);
    }

    @Override
    public void element(String name) throws IOException{
        impl.element(name);
        parentTypeStack.push(TYPE_OBJECT);
        setParentWriter();
    }

    @Override
    public void pop() throws IOException {
        impl.pop();
    }

    private void setParentWriter() {
        if(parentTypeStack.isEmpty()){
            return;
        }
        switch (parentTypeStack.peek()){
            case TYPE_ARRAY:
                pWriter = mArrayWriter;
                break;
            case TYPE_OBJECT:
                pWriter = mObjWriter;
                break;
        }
    }
}
