package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.ReflectyContext;

/**
 * the reflecty reader
 * @author heaven7
 */
public interface ReflectyReader {

    /**
     * called on request next value as string
     * @return the value.
     */
    String nextString();

    /**
     * called on request next name
     * @return the name.
     */
    String nextName();

    /**
     * begin read an array
     */
    void beginArray();
    /**
     * end read an array
     */
    void endArray();

    /**
     * called when have next element for current object/array/map
     * @return true if has next
     */
    boolean hasNext();

    /**
     * begin read an object
     * @param context the context
     * @param clazz the class . map be object or map
     */
    void beginObject(ReflectyContext context, Class<?> clazz);//class can be self-object class and any map class

    /**
     * end read an object
     */
    void endObject();

    /**
     * skip the value
     */
    void skipValue();
}
