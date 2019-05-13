/*
 * Copyright 2019
 * heaven7(donshine723@gmail.com)

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.ReflectyContext;

import java.io.IOException;

/**
 * reflecty writer
 * @author heaven7
 */
public interface ReflectyWriter {

    /**
     * called this writer begin the write
     * @param obj begin to write target object
     * @throws IOException if an I/O error occurs
     * @since 1.0.4
     */
    void begin(Object obj) throws IOException;

    /**
     * called this writer end the write
     * @param obj end to write target object
     * @throws IOException if an I/O error occurs
     * @since 1.0.4
     */
    void end(Object obj) throws IOException;

    /**
     * write the string as name
     * @param s the name
     * @throws IOException if an I/O error occurs.
     */
    void name(String s) throws IOException;

    /**
     * write a null value
     * @throws IOException if an I/O error occurs.
     */
    void nullValue() throws IOException;

    /**
     * write a number as value
     * @param obj the number
     * @throws IOException if an I/O error occurs.
     */
    void value(Number obj) throws IOException;
    /**
     * write a boolean value
     * @param obj the boolean value
     * @throws IOException if an I/O error occurs.
     */
    void value(Boolean obj) throws IOException;
    /**
     * write a Character value
     * @param obj the Character value
     * @throws IOException if an I/O error occurs.
     */
    void value(Character obj) throws IOException;
    /**
     * write a string value
     * @param obj the string value
     * @throws IOException if an I/O error occurs.
     */
    void value(String obj) throws IOException;

    /**
     * mark the writer begin to write an array. can called from array/list
     * @throws IOException if an I/O error occurs.
     */
    void beginArray() throws IOException;
    /**
     * mark the writer write the last array end. can called from array/list
     * @throws IOException if an I/O error occurs.
     */
    void endArray() throws IOException;

    /**
     * mark to write an object. can be called from object and map.
     * @param context  the context
     * @param clazz the class
     * @throws IOException if an I/O error occurs.
     */
    void beginObject(ReflectyContext context, Class<?> clazz) throws IOException;//class can be self-object class and any map class

    /**
     * mark to write object end.
     * @throws IOException if an I/O error occurs.
     */
    void endObject() throws IOException;

    /**
     * flush the content to the writer
     * @throws IOException throws io-exception
     */
    void flush() throws IOException;
}
