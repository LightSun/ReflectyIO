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

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.IOException;
import java.io.Reader;

/**
 * the xml reader
 * @author heaven7
 */
public final class XmlReader implements ReflectyReader {

    private final XmlTree mRoot;
    private Tree mParent;
    private Tree mCurrent;

    public XmlReader(Reader reader) {
        XmlReaderImpl.Element element = new XmlReaderImpl().parse(reader);
        XmlTree xmlTree = new XmlTree(element);
        travel(xmlTree);

        mCurrent = mRoot = xmlTree;
    }

    private void travel(XmlTree parent) {
        for (int i = 0 , size = parent.element.getChildCount() ; i < size ; i ++){
            XmlReaderImpl.Element ele = parent.element.getChild(i);
            XmlTree childTree = new XmlTree(ele);
            parent.addChild(childTree);
            travel(childTree);
        }
    }

    @Override
    public void begin() {

    }
    @Override
    public void end() {
        mRoot.reset();
        mCurrent = mRoot;
        mParent = null;
    }

    @Override
    public String nextString() throws IOException {
        String value = mCurrent.getValue();
        mCurrent = null;
        return value;
    }

    @Override
    public String nextName() throws IOException {
        return mCurrent.getName();
    }

    @Override
    public void skipValue() throws IOException {
        mCurrent = null;
    }

    @Override
    public boolean hasNext() throws IOException {
        if(mCurrent == null ){
            mCurrent = mParent.nextChild();
        }
        return mCurrent != null;
    }

    @Override
    public void beginArray()  throws IOException{
        mParent = mCurrent;
        mCurrent = null;
    }

    @Override
    public void endArray() throws IOException {
        mParent = mParent.getParent();
        mCurrent = null;
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz)  throws IOException{
        mParent = mCurrent;
        mCurrent = null;
    }

    @Override
    public void endObject()  throws IOException{
        mParent = mParent.getParent();
        mCurrent = null;
    }

    public String readBodyText() throws IOException{
        return mCurrent.getBodyText();
    }
}
