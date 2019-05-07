package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.base.util.Platforms;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.util.Stack;

public class YamlWriter implements ReflectyWriter {

    public static final byte TYPE_ARRAY  = 1;
    public static final byte TYPE_OBJECT = 2;
    private final HostWriterImpl mImpl = new HostWriterImpl();
    private final StringBuilder sb = new StringBuilder();
    private final int step = 2;
    private final String space;
    private final Stack<Byte> parentStack = new Stack<>();

    private ParentTypeWriter writer;
    private ParentArrayWriter arrayWriter;
    private ParentObjectWriter objectWriter;

    public YamlWriter(){
        this(2);
    }
    public YamlWriter(int count) {
        space = getSpace(count);
    }

    @Override
    public void name(String s) {
        writer.name(s);
    }

    @Override
    public void nullValue() {
        writer.nullValue();
    }

    @Override
    public void value(Number obj) {
        writer.value(obj);
    }
    @Override
    public void value(Boolean obj) {
        writer.value(obj);
    }

    @Override
    public void value(Character chz) {
        writer.value(chz);
    }
    @Override
    public void value(String str) {
        writer.value(str);
    }

    @Override
    public void beginArray() {
        parentStack.push(TYPE_ARRAY);
        setWriterInternal();
        mImpl.append(Platforms.getNewLine());
        String space = mImpl.indentSpace(1);
        mImpl.append(space).append("- ");
        //TODO
    }

    @Override
    public void endArray() {
        parentStack.pop();
        setWriterInternal();
    }

    @Override
    public void beginObject(Class<?> mClazz) {
        parentStack.push(TYPE_OBJECT);
        setWriterInternal();
    }

    @Override
    public void endObject() {
        parentStack.pop();
        setWriterInternal();
    }

    private void setWriterInternal() {
        switch (parentStack.peek()){
            case TYPE_ARRAY:
                if(arrayWriter == null){
                    arrayWriter = new ParentArrayWriter(mImpl);
                }
                writer = arrayWriter;
                break;

            case TYPE_OBJECT:
                if(objectWriter == null){
                    objectWriter = new ParentObjectWriter(mImpl);
                }
                writer = objectWriter;
                break;

             default:
                 throw new UnsupportedOperationException();
        }
    }

    private static String getSpace(int count) {
        StringBuilder sb_space = new StringBuilder();
        for (int i = 0 ; i< count ; i ++){
            sb_space.append(" ");
        }
        return sb_space.toString();
    }

    private class HostWriterImpl implements HostWriter{

        private int indentCount;
        private String totalIndentSpace;
        private final Stack<Integer> stepHistory = new Stack<>();

        public String indentSpace(int count){
            indentCount += count;
            stepHistory.push(count);
            return totalIndentSpace = getSpace(indentCount);
        }

        @Override
        public String nextSpace() {
            indentCount += step;
            stepHistory.push(step);
            return totalIndentSpace = getSpace(indentCount);
        }
        @Override
        public String currentSpace() {
            return totalIndentSpace;
        }
        @Override
        public String previousSpace() {
            indentCount -= stepHistory.pop();
            return totalIndentSpace = getSpace(indentCount);
        }
        @Override
        public HostWriter append(String str) {
            sb.append(str);
            return this;
        }
    }

}
