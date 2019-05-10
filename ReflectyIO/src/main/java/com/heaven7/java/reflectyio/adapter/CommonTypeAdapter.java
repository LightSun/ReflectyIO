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

import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.lang.reflect.Constructor;

/**
 * the common type adapter
 * @param <T> the basic type . only support byte, short, int, long, float, double, boolean and their wrapper types.
 * @author heaven7
 */
public class CommonTypeAdapter<T> extends AbstractBasicTypeAdapter {

    private final Constructor<T> constructor;

    public CommonTypeAdapter(ReflectyEvaluator evaluator, Class<T> clazz) {
        super(evaluator);
        try {
            this.constructor = clazz.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected TypeAdapter<ReflectyWriter, ReflectyReader> onCreateNameTypeAdapter() {
        return new NameTypeAdapter(getEvaluator(), constructor);
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        if(obj == null){
            sink.nullValue();
            return getEvaluator().evaluateNullSize();
        }else {
            if(obj instanceof Number){ //byte, short, int, long, float, double. Boolean.
                sink.value((Number)obj);
                return getEvaluator().evaluateNumber((Number)obj);
            }else if(obj instanceof Boolean){
                sink.value((Boolean) obj);
                return getEvaluator().evaluateBoolean((Boolean) obj);
            }else {
                throw new UnsupportedOperationException("maybe your type is not matched with actual type.");
            }
        }
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        String str = source.nextString();
        if(str != null){
            try {
                return constructor.newInstance(str);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static class NameTypeAdapter extends AbstractTypeAdapter{

        final Constructor<?> constructor;

        NameTypeAdapter(ReflectyEvaluator evaluator, Constructor<?> constructor) {
            super(evaluator);
            this.constructor = constructor;
        }

        @Override
        public int write(ReflectyWriter sink, Object obj) throws IOException {
            if(obj != null){
                sink.name(obj.toString());
                return getEvaluator().evaluateString(obj.toString());
            }
            return 0;
        }

        @Override
        public Object read(ReflectyReader source) throws IOException {
            String str = source.nextName();
            try {
                return constructor.newInstance(str);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
