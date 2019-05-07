package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflecty.Wrapper;
import com.heaven7.java.reflecty.iota.BasicTypeAdapter;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class MapTypeAdapter extends AbstractTypeAdapter {

    private final ReflectyContext mContext;
    private final Class<?> mMapClass;
    private final TypeAdapter<ReflectyWriter, ReflectyReader> mKeyAdapter;
    private final TypeAdapter<ReflectyWriter, ReflectyReader> mValueAdapter;

    public MapTypeAdapter(ReflectyEvaluator evaluator, ReflectyContext mContext, Class<?> mMapClass,
                          TypeAdapter<ReflectyWriter, ReflectyReader> mKeyAdapter,
                          TypeAdapter<ReflectyWriter, ReflectyReader> mValueAdapter) {
        super(evaluator);
        this.mContext = mContext;
        this.mMapClass = mMapClass;
        this.mKeyAdapter = mKeyAdapter;
        this.mValueAdapter = mValueAdapter;
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        if(obj == null){
            sink.beginObject(mContext, mMapClass);
            sink.endObject();
            return 0;
        }
        Map map = mContext.getMap(obj);
        if(mKeyAdapter instanceof BasicTypeAdapter){
            TypeAdapter<ReflectyWriter, ReflectyReader> key = ((BasicTypeAdapter) mKeyAdapter).getNameTypeAdapter();
            Set<Map.Entry> set = map.entrySet();
            //if mKeyAdapter is not base type. it doesn't support nested object. (Gson limit)
            sink.beginObject(mContext, mMapClass);
            for (Map.Entry en : set){
                key.write(sink, en.getKey());
                mValueAdapter.write(sink, en.getValue());
            }
            sink.endObject();
        }else {
            TypeAdapter<ReflectyWriter, ReflectyReader> key = mKeyAdapter;
            Set<Map.Entry> set = map.entrySet();
            sink.beginArray();
            for (Map.Entry en : set){
                key.write(sink, en.getKey());
                mValueAdapter.write(sink, en.getValue());
            }
            sink.endArray();
        }
        return 0;
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        Map map = mContext.createMap(mMapClass);
        if(mKeyAdapter instanceof BasicTypeAdapter){
            TypeAdapter<ReflectyWriter, ReflectyReader> keyTA = ((BasicTypeAdapter) mKeyAdapter).getNameTypeAdapter();
            source.beginObject(mContext, mMapClass);
            while (source.hasNext()){
                Object key = keyTA.read(source);
                Object value = mValueAdapter.read(source);
                map.put(key, value);
            }
            source.endObject();
        }else {
            TypeAdapter<ReflectyWriter, ReflectyReader> keyTA = mKeyAdapter;
            source.beginArray();
            while (source.hasNext()){
                Object key = keyTA.read(source);
                Object value = mValueAdapter.read(source);
                map.put(key, value);
            }
            source.endArray();
        }
        return map instanceof Wrapper ? ((Wrapper) map).unwrap() : map;
    }
}
