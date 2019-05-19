/*
 * Copyright 2019
 * heaven7(donshine723@gmail.com)

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
