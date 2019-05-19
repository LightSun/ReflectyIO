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


import java.io.IOException;

/**
 * @author heaven7
 */
/*public*/ class ArrayTypeWriter implements ParentTypeWriter {

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void nullValue(XmlWriterImpl impl, String lastName) {
        sb.append("null,");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Number obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Boolean obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, Character obj) {
        sb.append(obj.toString()).append(",");
    }

    @Override
    public void value(XmlWriterImpl impl, String lastName, String obj) {
        sb.append(obj).append(",");
    }

    @Override
    public void endArray(XmlWriterImpl impl, String lastName) throws IOException {
        sb.deleteCharAt(sb.length()-1);
        String str = sb.toString();
        sb.delete(0, sb.length());
        impl.attribute(lastName, str);
    }
}
