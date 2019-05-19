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

/**
 * the GroupLine indicate the yaml line tree
 * @author heaven7
 */
/*public*/ class GroupLine {
    List<GroupLine> children = new ArrayList<>();
    GroupLine parent;
    YamlLine line;

    int childIndex = -1;

    public GroupLine() {
    }
    public GroupLine(YamlLine yl) {
        line = yl;
    }
    public void addChild(GroupLine gl) {
        children.add(gl);
        gl.parent = this;
    }

    public void reset(){
        childIndex = -1;
        for (GroupLine line : children){
            line.reset();
        }
    }
    public GroupLine nextChild(){
        childIndex += 1;
        if(children.size() > childIndex){
            return children.get(childIndex);
        }
        return null;
    }
    @Override
    public String toString() {
        return "GroupLine{" +
                "line=" + line +
                '}';
    }

   /* public boolean hasNextBrother() {
        return parent.children.size() > parent.childIndex + 1;
    }
    public GroupLine nextBrother() {
        parent.childIndex += 1;
        return parent.children.get(childIndex);
    }
    public boolean hasNextChild() {
        return children.size() > childIndex + 1;
    }*/
}
