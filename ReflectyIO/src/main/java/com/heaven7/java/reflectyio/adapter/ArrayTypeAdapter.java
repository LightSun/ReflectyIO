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
package com.heaven7.java.reflectyio.adapter;


import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflecty.member.BaseMemberProxy;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * the array type adapter.
 * @author heaven7
 */
public final class ArrayTypeAdapter extends AbstractTypeAdapter {

    private final TypeAdapter<ReflectyWriter, ReflectyReader> mComponentAdapter;
    private final Class<?> mComponentClass;

    public ArrayTypeAdapter(ReflectyEvaluator evaluator, Class<?> componentClass, TypeAdapter<ReflectyWriter, ReflectyReader> mComponentAdapter) {
        super(evaluator);
        this.mComponentClass = componentClass;
        this.mComponentAdapter = mComponentAdapter;
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        sink.beginArray();
        if(obj != null) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object o = Array.get(obj, i);
                mComponentAdapter.write(sink, o);
            }
        }
        sink.endArray();
        return 0;
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        List list = new ArrayList();
        source.beginArray();
        while (source.hasNext()){
            Object ele = mComponentAdapter.read(source);
            list.add(ele);
        }
        source.endArray();
        if(!mComponentClass.isPrimitive()){
            return list.toArray((Object[]) Array.newInstance(mComponentClass, list.size()));
        }else {
            switch (BaseMemberProxy.parseType(mComponentClass)){
                //TODO
                case MemberProxy.TYPE_BYTE:

                case MemberProxy.TYPE_SHORT:
                case MemberProxy.TYPE_INT:
                case MemberProxy.TYPE_LONG:

                case MemberProxy.TYPE_BOOLEAN:
                case MemberProxy.TYPE_FLOAT:
                case MemberProxy.TYPE_DOUBLE:
                case MemberProxy.TYPE_CHAR:
            }
            //can't reach here
            throw new UnsupportedOperationException();
        }
    }
}
