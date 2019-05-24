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

/**
 * the char adapter used to read and write char data
 *
 * @author heaven7
 */
public class BooleanAdapter extends AbstractBasicTypeAdapter {

    public BooleanAdapter(ReflectyEvaluator evaluator) {
        super(evaluator);
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        if (obj != null) {
            sink.value((Boolean) obj);
            return getEvaluator().evaluateBoolean(((Boolean) obj));
        } else {
            sink.nullValue();
            return getEvaluator().evaluateNullSize();
        }
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        return source.nextBoolean();
    }

    @Override
    protected TypeAdapter<ReflectyWriter, ReflectyReader> onCreateNameTypeAdapter() {
        return new NameTypeAdapter(getEvaluator());
    }

    private static class NameTypeAdapter extends AbstractTypeAdapter {

        public NameTypeAdapter(ReflectyEvaluator evaluator) {
            super(evaluator);
        }

        @Override
        public int write(ReflectyWriter sink, Object obj) throws IOException {
            if (obj != null) {
                sink.name(obj.toString());
                return getEvaluator().evaluateString(obj.toString());
            }
            return 0;
        }
        @Override
        public Object read(ReflectyReader source) throws IOException {
            String str = source.nextName();
            return Boolean.valueOf(str);
        }
    }
}
