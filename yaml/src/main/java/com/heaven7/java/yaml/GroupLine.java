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

import java.util.ArrayList;
import java.util.List;

/*public*/ class GroupLine {
    List<GroupLine> childen = new ArrayList<>();
    GroupLine parent;
    YamlLine line;

    int childIndex = -1;

    public GroupLine() {
    }
    public GroupLine(YamlLine yl) {
        line = yl;
    }
    public void addChild(GroupLine gl) {
        childen.add(gl);
        gl.parent = this;
    }
    public boolean hasNextBrother() {
        return parent.childen.size() > parent.childIndex + 1;
    }
    public GroupLine nextBrother() {
        parent.childIndex += 1;
        return parent.childen.get(childIndex);
    }
    public boolean hasNextChild() {
        return childen.size() > childIndex + 1;
    }
    public GroupLine nextChild(){
        childIndex += 1;
        if(childen.size() > childIndex){
            return childen.get(childIndex);
        }
        return null;
    }

    @Override
    public String toString() {
        return "GroupLine{" +
                "line=" + line +
                '}';
    }
}
