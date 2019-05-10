package com.heaven7.java.reflectyio;

/**
 * the reflecty evaluator
 * @author heaven7
 */
public interface ReflectyEvaluator {

    int evaluateObject(Object obj);

    int evaluateNullSize();

    int evaluateNumber(Number obj);

    int evaluateBoolean(Boolean obj);

    int evaluateString(String s);

    int evaluateCharacter(Character obj);

}
