package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.base.anno.VisibleForTest;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Predicate;

public class YamlReader implements ReflectyReader {

    private final GroupLine root = new GroupLine();
    private GroupLine parent;
    private GroupLine current;

    public YamlReader(Reader r) throws IOException{
        BufferedReader reader = r instanceof BufferedReader ? (BufferedReader) r : new BufferedReader(r);
        List<String> lines = new ArrayList<>();
        String line ;
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        analyzeLines(lines, root);
        current = root;
    }

    @VisibleForTest
    static void analyzeLines(List<String> lines, GroupLine root) {
        List<YamlLine> yls = new ArrayList<>();
        lines.removeIf(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.trim().isEmpty();
            }
        });
        for (int i = 0, len = lines.size(); i < len ; i ++) {
            YamlLine yl = YamlLine.parse(lines.get(i));
            yl.index = i;
            yls.add(yl);
        }
        List<YamlLine> list = new ArrayList<>(yls);
        /*
        1. travel all lines, push line to stack. and compare the intent between current and last.
        2. if indentCount ==  means is brother
           else if indentCount > means is child.
           or else reach the last' parent. pop all same indent of last.
         *
         */
        GroupLine last = new GroupLine(list.get(0));
        root.addChild(last);
        Stack<GroupLine> stack = new Stack<>();
        stack.push(last);
        if(list.size() > 1){
            for (YamlLine line : list.subList(1, list.size())){
                GroupLine cur = new GroupLine(line);
                if(line.indentCount == last.line.indentCount){
                    last.parent.addChild(cur);
                }else if(line.indentCount > last.line.indentCount){
                    last.addChild(cur);
                }else {
                    //remove last-batch lines.
                    GroupLine temp = stack.pop();
                    while (line.indentCount < temp.line.indentCount){
                         temp = stack.pop();
                    }
                    temp.parent.addChild(cur);
                }
                stack.push(cur);
                last = cur;
            }
        }
    }
    @Override
    public String nextString() {
        String str = current.line.value;
        current = null;
        return str;
    }
    @Override
    public void skipValue() {
        current = null;
    }

    @Override
    public String nextName() {
        return current.line.name;
    }

    @Override
    public boolean hasNext() {
        if(current == null){
            current = parent.nextChild();
        }
        return current != null;
    }

    @Override
    public void beginArray() {
        parent = current;
        current = null;
    }

    @Override
    public void endArray() {
        parent = parent.parent;
        current = null;
    }
    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) {
        parent = current;
        current = null;
    }
    @Override
    public void endObject() {
        parent = parent.parent;
        current = null;
    }

}
