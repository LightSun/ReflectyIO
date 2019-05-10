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
