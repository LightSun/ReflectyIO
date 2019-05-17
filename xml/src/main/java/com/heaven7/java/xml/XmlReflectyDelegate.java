package com.heaven7.java.xml;

import com.heaven7.java.reflecty.member.FieldProxy;
import com.heaven7.java.reflecty.member.MethodProxy;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class XmlReflectyDelegate extends SimpleReflectyDelegate{

    @Override
    public boolean shouldIncludeField(Field field, ReflectyField fieldAnno, boolean isInherit) {
        if((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT){
            return false;
        }
        return super.shouldIncludeField(field, fieldAnno, isInherit);
    }

    @Override
    public FieldProxy createFieldProxy(Class<?> owner, ReflectyClass classDesc, Field field, String property, ReflectyField fn) {
        XmlBody body = field.getAnnotation(XmlBody.class);
        if(body != null){
            return new XmlBodyProxy(owner, field, property);
        }
        return new XmlFieldProxy(owner, field, property);
    }
    @Override
    public MethodProxy createMethodProxy(Class<?> owner, ReflectyClass classDesc, Method get, Method set, String property, ReflectyMethod mn) {
        return new XmlMethodProxy(owner, get, set, property);
    }
}
