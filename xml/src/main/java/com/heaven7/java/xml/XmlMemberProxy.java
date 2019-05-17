package com.heaven7.java.xml;

import com.heaven7.java.reflecty.MemberProxy;

/**
 * indicate the xml member proxy
 * @author heaven7
 */
/*public*/ interface XmlMemberProxy extends MemberProxy{

    String nextElementName();

    String currentElementName();

    boolean hasNextElementName();

    void beginElement(Object ref);

    void endElement();

   default String elementName(){
        if(hasNextElementName()){
            return nextElementName();
        }
        return currentElementName();
    }
}
