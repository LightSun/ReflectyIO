package com.heaven7.java.reflectyio.adapter;


import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
        return list.toArray((Object[]) Array.newInstance(mComponentClass, list.size()));
    }
}
