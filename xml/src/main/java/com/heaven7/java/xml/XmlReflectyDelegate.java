package com.heaven7.java.xml;

import com.heaven7.java.reflecty.member.FieldProxy;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;

import java.lang.reflect.Field;

public class XmlReflectyDelegate extends SimpleReflectyDelegate{

    @Override
    public FieldProxy createFieldProxy(Class<?> owner, ReflectyClass classDesc, Field field, String property, ReflectyField fn) {
        XmlBody body = field.getAnnotation(XmlBody.class);
        if(body != null){
            return new XmlBodyProxy(owner, field, property);
        }
        return super.createFieldProxy(owner, classDesc, field, property, fn);
    }
}
