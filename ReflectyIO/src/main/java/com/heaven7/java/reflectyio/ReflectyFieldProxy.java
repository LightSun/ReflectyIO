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

import com.heaven7.java.reflecty.member.FieldProxy;
import com.heaven7.java.reflectyio.anno.Since;
import com.heaven7.java.reflectyio.anno.Until;

import java.lang.reflect.Field;

/**
 * the field proxy of reflecty.
 * @author heaven7
 */
public class ReflectyFieldProxy extends FieldProxy implements VersionMemberProxy {

    private final float since;
    private final float until;

    public ReflectyFieldProxy(Class<?> ownerClass, Field field, String property) {
        super(ownerClass, field, property);
        Since u = field.getAnnotation(Since.class);
        Until n = field.getAnnotation(Until.class);
        this.since = u != null ? u.value() : -1f;
        this.until = n != null ? n.value() : -1f;
        if(until > 0 && until <= since){
            throw new IllegalStateException("until must larger than since.");
        }
    }
    @Override
    public boolean isVersionMatched(float expectVersion) {
        if(since < 0){
            return true;
        }
        if(expectVersion >= since){
            if(until >= 0){
                return expectVersion < until;
            }else {
                return true;
            }
        }
        return false;
    }
}
