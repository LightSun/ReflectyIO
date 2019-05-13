package com.heaven7.java.xml;


import java.io.IOException;

/*public*/ class ArrayTypeWriter implements ParentTypeWriter {

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void nullValue(XmlWriterImpl impl, String lastName) {
        sb.append("null,");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Number obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Boolean obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Character obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, String obj) {
        sb.append(obj).append(",");
    }

    @Override
    public void endArray(XmlWriterImpl impl, String lastName) throws IOException {
        sb.deleteCharAt(sb.length()-1);
        String str = sb.toString();
        sb.delete(0, sb.length());
        impl.attribute(lastName, str);
    }
    @Override
    public void endObject(XmlWriterImpl impl, String lastName) {

    }
}
