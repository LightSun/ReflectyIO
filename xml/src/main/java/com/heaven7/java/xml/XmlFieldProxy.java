package com.heaven7.java.xml;

import com.heaven7.java.reflectyio.ReflectyFieldProxy;

import java.lang.reflect.Field;

public class XmlFieldProxy extends ReflectyFieldProxy implements XmlMemberProxy{

    private String elementPath;
    private String[] elementNames;
    private int index = -1;

    public XmlFieldProxy(Class<?> ownerClass, Field field, String property) {
        super(ownerClass, field, property);
    }

    @Override
    public String nextElementName(){
        if(elementPath == null){
            XmlElement element = getField().getAnnotation(XmlElement.class);
            if(element == null){
                throw new XmlException("for complex xml element. you must define the element names. like 'root/person/...'");
            }
            elementPath = element.value();
            elementNames = elementPath.split("/");
        }
        if(index + 1 > elementNames.length){
            return null;
        }
        index ++;
        return elementNames[index];
    }
}
