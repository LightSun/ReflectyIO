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
package com.heaven7.java.xml;

import java.io.IOException;

/**
 * the xml writer
 * @author heaven7
 */
public interface IXmlWriter {

    /**
     * write a body text to current element
     * @param value the body object
     * @throws IOException if an I/O occurs
     */
    void bodyText(Object value) throws IOException;

    /**
     * write an attribute
     * @param name the attribute name
     * @param value the attribute value
     * @throws IOException  if an I/O occurs
     */
    void attribute(String name, Object value) throws IOException;

    /**
     * start a new element.
     * @param name the element name
     * @throws IOException if an I/O occurs
     */
    void element(String name) throws IOException;

    /**
     * end current element
     * @throws IOException if an I/O occurs
     */
    void pop() throws IOException;
}
