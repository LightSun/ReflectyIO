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
package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;
import com.heaven7.java.xml.XmlReader;
import com.heaven7.java.xml.XmlTypeAdapterManager;
import com.heaven7.java.xml.XmlWriter;

import java.io.Reader;
import java.io.Writer;

/**
 * the xml plugin
 * @author heaven7
 */
public final class XmlReflectyPlugin implements ReflectyPlugin {
    @Override
    public ITypeAdapterManager<ReflectyWriter, ReflectyReader> createTypeAdapterManager() {
        return new XmlTypeAdapterManager();
    }
    @Override
    public ReflectyWriter createReflectyWriter(Writer writer) {
        return new XmlWriter(writer);
    }
    @Override
    public ReflectyReader createReflectyReader(Reader reader) {
        return new XmlReader(reader);
    }
}
