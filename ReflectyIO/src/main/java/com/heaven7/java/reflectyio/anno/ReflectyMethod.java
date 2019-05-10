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
package com.heaven7.java.reflectyio.anno;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * indicate the reflecty method
 * @author heaven7
 * @see com.heaven7.java.reflecty.ReflectyDelegate#getPropertyFromMethod(Method, Annotation)
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyMethod {

    /**
     * indicate the serialize and deserialize name of method.
     * @return the serialize and deserialize name of method or empty indicate get default name from method.
     * @see com.heaven7.java.reflectyio.SimpleReflectyDelegate
     */
    String value() default "";
}
