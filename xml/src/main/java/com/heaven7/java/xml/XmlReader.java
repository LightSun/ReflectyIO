package com.heaven7.java.xml;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.IOException;
import java.io.Reader;

public class XmlReader implements ReflectyReader {

    private Tree parent;
    private Tree current;

    public XmlReader(Reader reader) {
        XmlTree root = new XmlTree();
        root.addChild(new XmlTree(new XmlReaderImpl().parse(reader)));
        current = root;
    }

    @Override
    public String nextString() throws IOException {
        return current.getValue();
    }

    @Override
    public String nextName() throws IOException {
        return current.getName();
    }

    @Override
    public void skipValue()  throws IOException {
        current = null;
    }

    @Override
    public boolean hasNext()  throws IOException {
        if(current == null){
            current = parent.nextChild();
        }
        return current != null;
    }

    @Override
    public void beginArray()  throws IOException{
        parent = current;
        current = null;
    }

    @Override
    public void endArray() throws IOException {
        parent = current.getParent();
        current = null;
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz)  throws IOException{
        parent = current;
        current = null;
    }

    @Override
    public void endObject()  throws IOException{
        parent = current.getParent();
        current = null;
    }

    public String readBodyText() throws IOException{
        return current.getBodyText();
    }
}
