package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.iota.BasicTypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

public abstract class AbstractBasicTypeAdapter extends BasicTypeAdapter<ReflectyWriter, ReflectyReader> {

    private final ReflectyEvaluator evaluator;

    public AbstractBasicTypeAdapter(ReflectyEvaluator evaluator) {
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
