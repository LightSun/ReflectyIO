package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.base.util.Platforms;
import com.heaven7.java.reflectyio.ReflectyWriter;

public class YamlWriter implements ReflectyWriter {

    private final StringBuilder sb = new StringBuilder();
    private final String space;

    public YamlWriter(){
        this(2);
    }
    public YamlWriter(int count) {
        StringBuilder sb_space = new StringBuilder();
        for (int i = 0 ; i< count ; i ++){
            sb_space.append(" ");
        }
        this.space = sb_space.toString();
    }

    @Override
    public void name(String s) {
        nextSpaceString();
        sb.append(s).append(": ");
    }

    @Override
    public void nullValue() {
        sb.append(" ");
    }

    @Override
    public void value(Number obj) {
        sb.append(obj.toString());
    }
    @Override
    public void value(Boolean obj) {
        sb.append(obj.toString());
    }

    @Override
    public void value(Character chz) {
        sb.append((int)chz.charValue());
    }

    @Override
    public void value(String str) {
        sb.append(str);
    }

    @Override
    public void beginArray() {

    }

    @Override
    public void endArray() {

    }

    @Override
    public void beginObject(Class<?> mClazz) {
         sb.append(mClazz.getName()).append(":").append(Platforms.getNewLine());
    }

    @Override
    public void endObject() {

    }

    private void nextSpaceString() {
         sb.append(space);
    }
}
