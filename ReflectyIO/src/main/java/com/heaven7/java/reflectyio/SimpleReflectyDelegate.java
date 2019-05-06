package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.ReflectyDelegate;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflecty.member.FieldProxy;
import com.heaven7.java.reflecty.member.MethodProxy;
import com.heaven7.java.reflectyio.anno.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class SimpleReflectyDelegate implements ReflectyDelegate<TypeAdapter<ReflectyWriter, ReflectyReader>,
        ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> {

    @Override
    public void sort(List<MemberProxy> out) {

    }
    @Override
    public boolean isAllowInherit(Class<ReflectyClass> clazz) {
        return false;
    }

    @Override
    public boolean isAllowInherit(ReflectyInherit fieldInherit) {
        return fieldInherit == null || fieldInherit.value();
    }

    @Override
    public boolean isGetMethod(Method method, ReflectyMethod mn) {
        return method.getAnnotation(ReflectyMethodType.class).value() == ReflectyMethodType.TYPE_GET;
    }
    @Override
    public boolean isGetMethod(ReflectyMethod mn) {
        return false;
    }

    @Override
    public String getPropertyFromMethod(Method method, ReflectyMethod mn) {
        if(mn != null && !mn.value().isEmpty()){
            return mn.value();
        }
        String name = method.getName();
        if(name.startsWith("get") || name.startsWith("set")){
            return name.substring(3);
        }else{
            return name;
        }
    }

    @Override
    public String getPropertyFromField(Field field, ReflectyField fn) {
        return fn == null ? field.getName() : fn.property();
    }

    @Override
    public MethodProxy createMethodProxy(Class<?> owner, ReflectyClass classDesc, Method get, Method set, String property, ReflectyMethod mn) {
        return new ReflectyMethodProxy(owner, get, set, property);
    }
    @Override
    public FieldProxy createFieldProxy(Class<?> owner, ReflectyClass classDesc, Field field, String property, ReflectyField fn) {
        return new ReflectyFieldProxy(owner, field, property);
    }

    @Override
    public boolean shouldIncludeField(Field field, ReflectyField fieldAnno, boolean isInherit) {
        return true;
    }

    @Override
    public boolean shouldIncludeMethod(Method method, ReflectyMethod methodAnno, boolean isInherit) {
        ReflectyMethodType type = method.getAnnotation(ReflectyMethodType.class);
        return type != null;
    }

    @Override @SuppressWarnings("unchcked")
    public TypeAdapter<ReflectyWriter, ReflectyReader> performReflectClass(Class<?> clazz) {
        ReflectyClass rc = clazz.getAnnotation(ReflectyClass.class);
        if(rc == null){
            return null;
        }
        try {
            return rc.value().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
