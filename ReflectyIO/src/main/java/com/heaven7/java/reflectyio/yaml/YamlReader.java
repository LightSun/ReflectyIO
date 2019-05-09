package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.function.Predicate;

public class YamlReader implements ReflectyReader {

    private final GroupLine root = new GroupLine();
    private GroupLine current;
    private GroupLine last;

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
        1. 利用栈的思想. 一个一个的将line 入栈。 每个Indent和上一个比。indentCount == brother。> child.
         < 则将上一个同级的全部出栈。
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
        return current.line.value;
    }

    @Override
    public String nextName() {
        return current.line.name;
    }

    @Override
    public void beginArray() {
        current = current.next();
    }

    @Override
    public void endArray() {
        current = current.parent;
    }

    @Override
    public boolean hasNext() {
        return current.hasNext();
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> clazz) {
        current = current.next();
        //nextChild / nextBrother()?
    }
    @Override
    public void endObject() {
        current = current.parent;
    }
    @Override
    public void skipValue() {
    }

}
