package com.heaven7.java.xml;

import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.Commissioner;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.lang.reflect.Field;

/*public*/ class XmlBodyProxy extends XmlFieldProxy implements Commissioner{

    public XmlBodyProxy(Class<?> ownerClass, Field field, String property) {
        super(ownerClass, field, property);
    }

    @Override
    public void write(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, ReflectyWriter sink, Object value) throws IOException {
        assert sink instanceof XmlWriter;
        XmlWriter writer = (XmlWriter) sink;
        writer.bodyText(value);
    }

    @Override
    public void read(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, Object receiver, ReflectyReader source) throws IOException {
        assert source instanceof XmlReader;
        XmlReader xr = (XmlReader) source;
        try {
            setValue(receiver,  xr.readBodyText());
        } catch (IllegalAccessException e) {
            throw new XmlException(e);
        }
    }
}
