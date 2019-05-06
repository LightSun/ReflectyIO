package com.heaven7.java.reflectyio;

public class SimpleReflectyEvaluator implements ReflectyEvaluator {
    @Override
    public int evaluateObject(Object obj) {
        return 0;
    }

    @Override
    public int evaluateNullSize() {
        return 0;
    }

    @Override
    public int evaluateNumber(Number obj) {
        return 0;
    }

    @Override
    public int evaluateBoolean(Boolean obj) {
        return 0;
    }

    @Override
    public int evaluateString(String s) {
        return 0;
    }

    @Override
    public int evaluateCharacter(Character obj) {
        return 0;
    }
}
