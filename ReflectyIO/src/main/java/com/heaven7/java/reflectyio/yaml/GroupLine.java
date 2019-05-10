package com.heaven7.java.reflectyio.yaml;

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
