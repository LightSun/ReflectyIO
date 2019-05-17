package com.heaven7.java.xml;

import com.heaven7.java.reflectyio.ReflectyMethodProxy;

import java.lang.reflect.Method;

/**
 * the xml method proxy
 * @author heaven7
 */
/*public*/ class XmlMethodProxy extends ReflectyMethodProxy implements XmlMemberProxy, XmlMemberProxyHelper.Callback{

    private final XmlMemberProxyHelper mHelper;

    public XmlMethodProxy(Class<?> ownerClass, Method get, Method set, String property) {
        super(ownerClass, get, set, property);
        mHelper = new XmlMemberProxyHelper(this);
    }

    @Override
    public String nextElementName(){
        return mHelper.elementName(true);
    }
    @Override
    public boolean hasNextElementName() {
        return mHelper.hasNextName();
    }
    @Override
    public String currentElementName() {
        return mHelper.elementName(false);
    }

    @Override
    public void beginElement(Object ref) {

    }

    @Override
    public void endElement() {
        mHelper.reset();
    }
    @Override
    public XmlElement getXmlElement() {
        return getGetMethod().getAnnotation(XmlElement.class);
    }
}
