package com.heaven7.java.reflectyio.yaml;

import java.util.ArrayList;
import java.util.List;

public class GroupLine {
    List<GroupLine> childen = new ArrayList<>();
    GroupLine parent;
    YamlLine line;

    int childIndex = -1;

    public GroupLine() {
    }
    public GroupLine(YamlLine yl) {
        line = yl;
    }

    public boolean hasNext() {
        if (parent == null) {
            return false;
        }
        return parent.childen.size() > parent.childIndex + 1;
    }

    public GroupLine next() {
        parent.childIndex += 1;
        return parent.childen.get(parent.childIndex);
    }

    public void addChild(GroupLine gl) {
        childen.add(gl);
        gl.parent = this;
    }

    @Override
    public String toString() {
        return "GroupLine{" +
                "line=" + line +
                '}';
    }
}
