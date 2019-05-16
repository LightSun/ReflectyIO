package com.heaven7.java.xml;

public interface XmlMemberProxy {

    void beginElement(Object ref);

    String nextElementName();

    void endElement();

}
