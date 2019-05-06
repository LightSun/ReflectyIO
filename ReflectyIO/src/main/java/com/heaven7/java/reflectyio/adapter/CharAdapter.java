package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;

public class CharAdapter extends AbstractBasicTypeAdapter{

    public CharAdapter(ReflectyEvaluator evaluator) {
        super(evaluator);
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        if(obj != null){
            sink.value((Character)obj);
            return getEvaluator().evaluateCharacter((Character)obj);
        }else {
            sink.nullValue();
            return getEvaluator().evaluateNullSize();
        }
    }
    @Override
    public Object read(ReflectyReader source) throws IOException {
        String str = source.nextString();
        if(str != null){
            return (char) Integer.parseInt(str);
        }
        return null;
    }
    @Override
    protected TypeAdapter<ReflectyWriter, ReflectyReader> onCreateNameTypeAdapter() {
        return new NameTypeAdapter(getEvaluator());
    }

    private static class NameTypeAdapter extends AbstractTypeAdapter{

        public NameTypeAdapter(ReflectyEvaluator evaluator) {
            super(evaluator);
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
            return (char) Integer.parseInt(str);
        }
    }
}
