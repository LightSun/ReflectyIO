package com.heaven7.java.xml;

import com.heaven7.java.reflectyio.ReflectyFieldProxy;

import java.lang.reflect.Field;

/**
 * the xml field proxy
 * @author heaven7
 */
/*public*/ class XmlFieldProxy extends ReflectyFieldProxy implements XmlMemberProxy, XmlMemberProxyHelper.Callback{

    private final XmlMemberProxyHelper mHelper;

    public XmlFieldProxy(Class<?> ownerClass, Field field, String property) {
        super(ownerClass, field, property);
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
        return getField().getAnnotation(XmlElement.class);
    }
}
