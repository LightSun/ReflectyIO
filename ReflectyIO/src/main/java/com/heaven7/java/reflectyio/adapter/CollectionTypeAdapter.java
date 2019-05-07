package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflecty.Wrapper;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.util.Collection;

public final class CollectionTypeAdapter extends AbstractTypeAdapter {

    private final TypeAdapter<ReflectyWriter, ReflectyReader> mComponentAdapter;
    private final ReflectyContext mContext;
    private final Class<?> mCollClazz;

    public CollectionTypeAdapter(ReflectyEvaluator evaluator, ReflectyContext mContext, Class<?> collClazz, TypeAdapter<ReflectyWriter, ReflectyReader> mComponentAdapter) {
        super(evaluator);
        this.mContext = mContext;
        this.mCollClazz = collClazz;
        this.mComponentAdapter = mComponentAdapter;
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        sink.beginArray();
        if(obj != null) {
            Collection coll = mContext.getCollection(obj);
            for (Object element : coll) {
                mComponentAdapter.write(sink, element);
            }
        }
        sink.endArray();
        return 0;
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        Collection coll = mContext.createCollection(mCollClazz);
        source.beginArray();
        while (source.hasNext()){
            Object ele = mComponentAdapter.read(source);
            coll.add(ele);
        }
        source.endArray();
        return coll instanceof Wrapper ? ((Wrapper) coll).unwrap() : coll;
    }
}
