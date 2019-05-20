package com.heaven7.java.xml;
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
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.Commissioner;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * the xml body proxy.
 * @author heaven7
 */
/*public*/ class XmlBodyProxy extends XmlFieldProxy implements Commissioner{

    public XmlBodyProxy(Class<?> ownerClass, Field field, String property) {
        super(ownerClass, field, property);
    }

    @Override
    public void write(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, ReflectyWriter sink, Object value) throws IOException {
        assert sink instanceof XmlWriter;
        XmlWriter writer = (XmlWriter) sink;
        writer.bodyText(value);
    }

    @Override
    public void read(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, Object receiver, ReflectyReader source) throws IOException {
        assert source instanceof XmlReader;
        XmlReader xr = (XmlReader) source;
        try {
            setValue(receiver,  xr.readBodyText());
            xr.skipValue();
        } catch (IllegalAccessException e) {
            throw new XmlException(e);
        }
    }
}
