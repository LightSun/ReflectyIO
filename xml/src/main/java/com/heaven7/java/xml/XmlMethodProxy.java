package com.heaven7.java.xml;

import com.heaven7.java.reflectyio.ReflectyMethodProxy;

import java.lang.reflect.Method;

public class XmlMethodProxy extends ReflectyMethodProxy implements XmlMemberProxy{

    private String elementPath;
    private String[] elementNames;
    private int index = -1;

    public XmlMethodProxy(Class<?> ownerClass, Method get, Method set, String property) {
        super(ownerClass, get, set, property);
    }

    @Override
    public String nextElementName() {
        if(elementPath == null){
            XmlElement element = getGetMethod().getAnnotation(XmlElement.class);
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
