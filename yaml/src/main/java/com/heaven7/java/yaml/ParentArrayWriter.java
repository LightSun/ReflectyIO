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
package com.heaven7.java.yaml;


import com.heaven7.java.base.util.Platforms;

/**
 * the parent array writer
 * @author heaven7
 */
/*public*/ class ParentArrayWriter extends BaseParentWriter implements ParentTypeWriter {

    public ParentArrayWriter(HostWriter hostWriter) {
        super(hostWriter);
    }
    @Override
    public void name(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullValue() {
       append(getHostWriter().currentSpace()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Number obj) {
        append(getHostWriter().currentSpace()).append(obj.toString()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Boolean obj) {
        append(getHostWriter().currentSpace()).append(obj.toString()).append(Platforms.getNewLine());
    }

    @Override
    public void value(Character chz) {
        append(getHostWriter().currentSpace()).append(chz.toString()).append(Platforms.getNewLine());
    }
    @Override
    public void value(String str) {
        append(getHostWriter().currentSpace()).append(str).append(Platforms.getNewLine());
    }
}
