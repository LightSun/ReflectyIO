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

import com.heaven7.java.base.anno.VisibleForTest;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflectyio.ReflectyReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * <p>the yaml reader used to read'yaml' data. support all data-types, even if self-list and self-map. a data demo like below.</p>
 * <pre><code>
 *   age: 28
 *   name: heaven7
 *   list:
 *     - h123
 *     - h456
 *     - h789
 *   map:
 *     h1: 1
 *     h2: 2
 *     h3: 3
 *   listMap:
 *       -
 *         h1: 1
 *         h2: 2
 *         h3: 3
 *       -
 *         h1: 1
 *         h2: 2
 *         h3: 3
 *   mapList:
 *     h1:
 *       - 1
 *       - 2
 *       - 3
 *     h2:
 *       - 4
 *       - 5
 *       - 6
 *   infoList:
 *       -
 *         addr: addr1
 *         phone: 12345
 *       -
 *         addr: addr1
 *         phone: 12345
 *   infoMap:
 *     info1:
 *       addr: addr1
 *       phone: 12345
 *     info2:
 *       addr: addr1
 *       phone: 12345
 *   info:
 *     addr: addr1
 *     phone: 12345
 * </code></pre>
 * they will be parse to a person object like:
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
 * </code></pre>
 *
 * <p>How to use ? </p>
 * <pre><code>
 *     YamlReader reader = new YamlReader(new StringReader(content));
 *         Object obj = new ReflectyIo().delegate(new SimpleReflectyDelegate())
 *                 .typeToken(tt)
 *                 .build().read(reader);
 * </code></pre>
 */
public class YamlReader implements ReflectyReader {

    private final GroupLine root = new GroupLine();
    private GroupLine parent;
    private GroupLine current;

    /**
     * create a yaml reader from target reader
     * @param r the reader
     * @throws IOException if read occurs exception
     */
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
                String trim = s.trim();
                return trim.isEmpty() || trim.startsWith("#");
            }
        });
        for (int i = 0, len = lines.size(); i < len ; i ++) {
            YamlLine yl = YamlLine.parse(lines.get(i));
            if(yl != null) {
                yl.index = i;
                yls.add(yl);
            }
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
    public void begin() throws IOException {

    }

    @Override
    public void end() throws IOException {
        current = root;
        parent = null;
        root.reset();
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
