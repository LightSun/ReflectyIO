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

import com.heaven7.java.reflecty.Reflecty;
import com.heaven7.java.reflecty.ReflectyBuilder;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyTypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyWriter;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyInherit;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;

/**
 * the xml type adapter manager
 * @author heaven7
 */
public class XmlTypeAdapterManager extends ReflectyTypeAdapterManager {

    public XmlTypeAdapterManager(){
        super(new ReflectyBuilder<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit>()
                .classAnnotation(ReflectyClass.class)
                .fieldAnnotation(ReflectyField.class)
                .methodAnnotation(ReflectyMethod.class)
                .inheritAnnotation(ReflectyInherit.class)
                .delegate(new XmlReflectyDelegate())
                .build());
    }

    public XmlTypeAdapterManager(Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty) {
        super(mReflecty);
    }

    public XmlTypeAdapterManager(ReflectyContext context, ReflectyEvaluator evaluator,
                                 Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty) {
        super(context, evaluator, mReflecty);
    }

    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createObjectTypeAdapter(Class<?> objectClazz, float applyVersion) {
        return new XmlObjectTypeAdapter(getReflectyEvaluator(), getReflecty(), this, objectClazz, applyVersion);
    }
}
