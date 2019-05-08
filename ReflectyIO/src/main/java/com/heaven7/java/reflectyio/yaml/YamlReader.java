package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

public class YamlReader implements ReflectyReader {

    private final GroupLine root = new GroupLine();
    private GroupLine current;

    private void analyzeLines(List<String> lines) {
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

       // YamlLine popLine = findPushPopLine(yls);
    }

    private GroupLine[] findPushPopLine(List<YamlLine> yls, GroupLine parent) {
        if(yls.isEmpty()){
            return null;
        }
        YamlLine line = yls.get(0);
        GroupLine[] arr = new GroupLine[2];
        arr[0] = new GroupLine(line);
        for (int i = 1, size = yls.size() ; i < size ; i ++){
            YamlLine temp = yls.get(i);
            if(temp.indentCount == line.indentCount){
                arr[1] = new GroupLine(temp);
                break;
            }
        }
        if(parent != null){

        }
        return arr;
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
    }
    @Override
    public void endObject() {
        current = current.parent;
    }
    @Override
    public void skipValue() {

    }

}
