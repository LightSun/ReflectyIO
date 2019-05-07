package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.base.util.Platforms;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

public class YamlWriter implements ReflectyWriter {

    public static final byte TYPE_ARRAY  = 1;
    public static final byte TYPE_OBJECT = 2;
    public static final byte TYPE_MAP    = 3;
    private final HostWriterImpl mImpl = new HostWriterImpl();
    private final StringBuilder sb = new StringBuilder();
    private final int step = 2;
    private final Stack<Byte> parentStack = new Stack<>();

    private ParentTypeWriter writer;
    private ParentArrayWriter arrayWriter;
    private ParentObjectWriter objectWriter;

    private final Writer realWriter;

    public YamlWriter(Writer realWriter) {
        this.realWriter = realWriter;
    }

    @Override
    public void flush() throws IOException {
        String str = sb.toString();
        realWriter.write(str);
        sb.delete(0, str.length());
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

        //if last type is map. and this is first.
        mImpl.indentSpace("  - ");
        mImpl.append(Platforms.getNewLine());

        System.out.println("beginArray >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void endArray() {
        parentStack.pop();
        setWriterInternal();

        mImpl.previousSpace();
        System.out.println("endArray >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> mClazz) {
        parentStack.push(TYPE_OBJECT);
        setWriterInternal();

        mImpl.append(Platforms.getNewLine());
        mImpl.nextSpace();
        System.out.println("beginObject >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void endObject() {
        parentStack.pop();
        setWriterInternal();

        mImpl.previousSpace();
        System.out.println("endObject >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    private void setWriterInternal() {
        if(parentStack.isEmpty()){
            return;
        }
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

        int indentCount;
        String totalIndentSpace ="";
        final Stack<StepNode> stepHistory = new Stack<>();

        public String indentSpace(String holdStr){
            indentCount += holdStr.length();
            stepHistory.push(new StepNode(holdStr));
            totalIndentSpace += holdStr;
            return totalIndentSpace;
        }
        public String indentSpace(int count){
            indentCount += count;
            stepHistory.push(new StepNode(count));
            totalIndentSpace += getSpace(count);
            return totalIndentSpace;
        }

        @Override
        public String nextSpace() {
            return indentSpace(step);
        }
        @Override
        public String currentSpace() {
            return totalIndentSpace;
        }
        @Override
        public String previousSpace() {
            int lastStep = stepHistory.pop().step;
            indentCount -= lastStep;
            return totalIndentSpace = totalIndentSpace.substring(0, totalIndentSpace.length() - lastStep);
        }
        @Override
        public HostWriter append(String str) {
            sb.append(str);
            return this;
        }
    }

    private static class StepNode{
        final int step;
        final String holdStr;

        public StepNode(int step) {
            this.step = step;
            this.holdStr = getSpace(step);
        }
        public StepNode(String holdStr) {
            this.step = holdStr.length();
            this.holdStr = holdStr;
        }
    }

}
