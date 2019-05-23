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

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ObjectWriteMonitor;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 * the xml writer
 * @author heaven7
 */
public final class XmlWriter implements ReflectyWriter, IXmlWriter, ObjectWriteMonitor {

    private static final byte TYPE_OBJECT = 1;
    private static final byte TYPE_ARRAY  = 2;
    private static final byte TYPE_MAP    = 3;

    private final ArrayTypeWriter mArrayWriter = new ArrayTypeWriter();
    private final ObjectTypeWriter mObjWriter  = new ObjectTypeWriter();

    private final LinkedList<StackNode> parentTypeStack = new LinkedList<>();
    private final XmlWriterImpl impl;
    private ParentTypeWriter pWriter;
    private String name;

    /** from {@linkplain XmlElement} */
    private String mElementName;
    private final LinkedList<XmlMemberProxy> mProxyStack = new LinkedList<>();

    public XmlWriter(Writer writer) {
        this.impl = new XmlWriterImpl(writer);
    }

    @Override
    public void begin(Object obj) throws IOException {
    }

    @Override
    public void end(Object obj) throws IOException{
        parentTypeStack.clear();
        mProxyStack.clear();
        mElementName = null;
        name = null;
    }
    @Override
    public void name(String s) throws IOException {
       // System.out.println("name: " + s);
        this.name = s;
    }

    @Override
    public void nullValue() throws IOException {
        pWriter.nullValue(impl, name);
    }

    @Override
    public void value(Number obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(Boolean obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(Character obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void value(String obj) throws IOException {
        pWriter.value(impl, name, obj);
    }

    @Override
    public void beginArray() throws IOException {
        parentTypeStack.push(new StackNode(TYPE_ARRAY));
        setParentWriter();
    }

    @Override
    public void endArray() throws IOException {
        pWriter.endArray(impl, name);
        name = null;
        StackNode node = parentTypeStack.pop();
        while (node.elementCount > 0){
            impl.pop();
            node.elementCount -- ;
        }
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) throws IOException {
        //last is list. and this is map ? add child element ?
        boolean isMap = context.isMap(clazz);
        byte lastType = -1;
        if(!parentTypeStack.isEmpty()){
            lastType = parentTypeStack.peek().type;
        }
        XmlMemberProxy xmp = !mProxyStack.isEmpty() ? mProxyStack.peek() : null;

        StackNode node = new StackNode(isMap ? TYPE_MAP : TYPE_OBJECT);
        if(lastType == TYPE_ARRAY){
            makeLastNameAsElement();
            doMakeElementName(xmp, node, false);
        }else {
            if(isMap){
                makeLastNameAsElement();
            }else {
                doMakeElementName(xmp, node, lastType == TYPE_MAP);
            }
        }

        parentTypeStack.push(node);
        setParentWriter();
    }

    private void doMakeElementName(XmlMemberProxy xmp, StackNode node, boolean lastIsMap) throws IOException {
        String name;
        //last is map. means key often is the element name.
        if(lastIsMap){
            name = this.name;
        }else {
            name = null;
        }
        //dynamic first
        if (name == null && xmp != null) {
            name = xmp.elementName();
        }
        if(name == null && mElementName != null){
            name = mElementName;
        }
        if (name == null) {
            throw new XmlException("you must config the xml element name by @XmlElement. it can often used for Class, field, and method.");
        }
        impl.element(name);
        node.elementCount++;
    }

    private void makeLastNameAsElement() throws IOException {
        if(this.name != null){
            impl.element(this.name);
            this.name = null;
            //mark this to last stack node.
            parentTypeStack.peek().elementCount++;
        }
    }

    @Override
    public void endObject() throws IOException {
        name = null;
        impl.pop();
        parentTypeStack.pop();
    }

    @Override
    public void flush() throws IOException {
        impl.flush();
    }

    public void bodyText(Object value) throws IOException{
        impl.text(value);
    }
    @Override
    public void attribute(String name, Object value) throws IOException{
        impl.attribute(name, value);
    }
    @Override
    public void element(String name) throws IOException{
        impl.element(name);
        parentTypeStack.push(new StackNode(TYPE_OBJECT));
        setParentWriter();
    }

    @Override
    public void pop() throws IOException {
        impl.pop();
    }

    private void setParentWriter() {
        if(parentTypeStack.isEmpty()){
            return;
        }
        switch (parentTypeStack.peek().type){
            case TYPE_ARRAY:
                pWriter = mArrayWriter;
                break;
            case TYPE_OBJECT:
            case TYPE_MAP:
                pWriter = mObjWriter;
                break;
        }
    }

    //---------------------------------------------------

    @Override
    public void beginWriteObject(ReflectyContext context, Class<?> defineClass, Object obj) {
        XmlElement xe = defineClass.getAnnotation(XmlElement.class);
        if(xe == null && obj != null){
            xe = obj.getClass().getAnnotation(XmlElement.class);
        }
        if(xe != null){
            mElementName = xe.value();
        }else {
            mElementName = defineClass.getSimpleName();
        }
    }

    @Override
    public void endWriteObject() {
        mElementName = null;
    }

    @Override
    public void beginWriteMemberProxy(ReflectyContext context, MemberProxy proxy) {
        //System.out.println(proxy.getPropertyName());
        try {
            XmlMemberProxy mProxy = (XmlMemberProxy) proxy;
            mProxy.beginElement(this);
            mProxyStack.push(mProxy);
        }catch (ClassCastException e){
            throw new RuntimeException("for xml, member proxy should impl XmlMemberProxy.");
        }
    }

    @Override
    public void endWriteMemberProxy() {
        mProxyStack.pop().endElement();
       // System.out.println("-------- current:  \n   " + impl.getBaseWriter());
    }

    static class StackNode{
        final byte type;
        byte elementCount;
        public StackNode(byte type) {
            this.type = type;
        }
    }
}
