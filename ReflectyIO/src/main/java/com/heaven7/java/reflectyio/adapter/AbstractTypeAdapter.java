package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

public abstract class AbstractTypeAdapter extends TypeAdapter<ReflectyWriter, ReflectyReader> {

    private final ReflectyEvaluator evaluator;

    public AbstractTypeAdapter(ReflectyEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public ReflectyEvaluator getEvaluator() {
        return evaluator;
    }

    @Override
    public int evaluateSize(Object obj) {
        return evaluator.evaluateObject(obj);
    }
}
