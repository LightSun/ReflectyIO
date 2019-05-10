package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.ReflectyContext;

import java.io.IOException;

/**
 * reflecty writer
 * @author heaven7
 */
public interface ReflectyWriter {

    /**
     * write the string as name
     * @param s the name
     */
    void name(String s);

    /**
     * write a null value
     */
    void nullValue();

    /**
     * write a number as value
     * @param obj the number
     */
    void value(Number obj);
    /**
     * write a boolean value
     * @param obj the boolean value
     */
    void value(Boolean obj);
    /**
     * write a Character value
     * @param obj the Character value
     */
    void value(Character obj);
    /**
     * write a string value
     * @param obj the string value
     */
    void value(String obj);

    /**
     * mark the writer begin to write an array. can called from array/list
     */
    void beginArray();
    /**
     * mark the writer write the last array end. can called from array/list
     */
    void endArray();

    /**
     * mark to write an object. can be called from object and map.
     * @param context  the context
     * @param clazz the class
     */
    void beginObject(ReflectyContext context, Class<?> clazz);//class can be self-object class and any map class

    /**
     * mark to write object end.
     */
    void endObject();

    /**
     * flush the content to the writer
     * @throws IOException throws io-exception
     */
    void flush() throws IOException;
}
