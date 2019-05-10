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

/**
 * indicate the inherit relationship. this is useful for self-object with its' 'extend'.
 * @author heaven7
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReflectyInherit {

    /**
     * indicate can inherit to child or not.
     * @return true if can inherit.
     * @see com.heaven7.java.reflecty.ReflectyDelegate#isAllowInherit(Annotation)
     */
    boolean value() default true;
}
