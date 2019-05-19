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

import com.heaven7.java.reflecty.member.FieldProxy;
import com.heaven7.java.reflecty.member.MethodProxy;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * the xml reflecty delegate
 * @author heaven7
 */
public class XmlReflectyDelegate extends SimpleReflectyDelegate{

    @Override
    public boolean shouldIncludeField(Field field, ReflectyField fieldAnno, boolean isInherit) {
        if((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT){
            return false;
        }
        return super.shouldIncludeField(field, fieldAnno, isInherit);
    }

    @Override
    public FieldProxy createFieldProxy(Class<?> owner, ReflectyClass classDesc, Field field, String property, ReflectyField fn) {
        XmlBody body = field.getAnnotation(XmlBody.class);
        if(body != null){
            return new XmlBodyProxy(owner, field, property);
        }
        return new XmlFieldProxy(owner, field, property);
    }
    @Override
    public MethodProxy createMethodProxy(Class<?> owner, ReflectyClass classDesc, Method get, Method set, String property, ReflectyMethod mn) {
        return new XmlMethodProxy(owner, get, set, property);
    }
}
