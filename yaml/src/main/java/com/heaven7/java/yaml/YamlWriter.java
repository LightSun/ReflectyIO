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

import com.heaven7.java.base.util.Platforms;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

/**
 * the writer used to write 'yaml' data.
 * <p>here is a person object with the person class.</p>
 * <pre><code>
 *     public class Person {
 *
     *     private int age;
     *     private String name;
     *     private List<String> list;
     *     private Map<String, Integer> map;
     *     private List<Map<String, Integer>> listMap;
     *     private Map<String, List<Integer>> mapList;
     *
     *     private List<Info> infoList;
     *     private Map<String, Info> infoMap;
     *     private Info info;
 *     }
 *     public class Info {
     *
     *     private String addr;
     *     private String phone;
 *     }
 *     // set person's property
 *      Person p = new Person();
 *         p.setAge(28);
 *         p.setName("heaven7");
 *         p.setList(createList());
 *         p.setMap(createMap());
 *         p.setMapList(createMapList());
 *         p.setListMap(createListMap());
 *         p.setInfo(createInfo());
 *         p.setInfoList(createInfoList());
 *         p.setInfoMap(createInfoMap());
 *
 *    // then write data
 *     new ReflectyIo().delegate(new SimpleReflectyDelegate())
 *                 .typeToken(tt)
 *                 .build().cacheTAM().write(new YamlWriter(...), p);
 * </code></pre>
 * @author heaven7
 */
public class YamlWriter implements ReflectyWriter {

    public static final byte TYPE_ARRAY  = 1;
    public static final byte TYPE_OBJECT = 2;
    public static final byte TYPE_MAP    = 3;
    private final HostWriterImpl mImpl = new HostWriterImpl();
    private final StringBuilder sb = new StringBuilder();
    private final int step = 2;
    private final LinkedList<Byte> parentStack = new LinkedList<>();

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
    public void begin(Object obj) throws IOException {

    }

    @Override
    public void end(Object obj) throws IOException {

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

        mImpl.indentSpace("  - ", false);
        mImpl.append(Platforms.getNewLine());

       // System.out.println("beginArray >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void endArray() {
        parentStack.pop();
        setWriterInternal();

        mImpl.previousSpace();
       // System.out.println("endArray >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void beginObject(ReflectyContext context, Class<?> mClazz) {
        boolean lastIsArray = !parentStack.isEmpty() && parentStack.peek() == TYPE_ARRAY;
        boolean currentIsMap = context.isMap(mClazz);
        parentStack.push(currentIsMap ? TYPE_MAP: TYPE_OBJECT);
        setWriterInternal();

        //if last is array. and current is map
        if(lastIsArray){
            mImpl.append(mImpl.totalIndentSpace).append("- ").append(Platforms.getNewLine());
            mImpl.nextSpace();
        }else {
            mImpl.append(Platforms.getNewLine());
            mImpl.nextSpace();
        }
       // System.out.println("beginObject >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
    }

    @Override
    public void endObject() {
        parentStack.pop();
        setWriterInternal();

        mImpl.previousSpace();
       // System.out.println("endObject >>> indent_space=" + mImpl.totalIndentSpace + ", len = " + mImpl.totalIndentSpace.length());
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

            case TYPE_MAP:
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

    private class HostWriterImpl implements HostWriter {

        int indentCount;
        String totalIndentSpace ="";
        final LinkedList<StepNode> stepHistory = new LinkedList<>();

        public String indentSpace(String holdStr, boolean common){
            indentCount += holdStr.length();
            String commonHoldStr = common ? holdStr : getSpace(holdStr.length());
            stepHistory.push(new StepNode(commonHoldStr, holdStr, common));
            totalIndentSpace += commonHoldStr; //use common
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
            StepNode node = stepHistory.peek();
            if(node != null && !node.sameHoldStr){
                int length = totalIndentSpace.length();
                return totalIndentSpace.substring(0, length - node.step) + node.currentHoldStr;
            }
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
        final String currentHoldStr; //can only apply to similar node
        final boolean sameHoldStr;

        public StepNode(int step) {
            this.step = step;
            currentHoldStr = holdStr = getSpace(step);
            sameHoldStr = true;
        }
        public StepNode(String commonHoldStr, String curHoldStr, boolean common) {
            this.step = commonHoldStr.length();
            this.currentHoldStr = curHoldStr;
            this.holdStr = commonHoldStr;
            sameHoldStr = common;
        }
    }

}
