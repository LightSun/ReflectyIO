package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.TypeToken;
import com.heaven7.java.reflectyio.entity.Info;
import com.heaven7.java.reflectyio.entity.Person;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class PluginTest {

    private final StringWriter mWriter = new StringWriter();

    private void clean(){
        StringBuffer buffer = mWriter.getBuffer();
        buffer.delete(0, buffer.length());
    }

    @Test
    public void testObject() throws Exception{
        Person p = new Person();
        p.setAge(28);
        p.setName("heaven7");
        p.setList(createList());
        p.setMap(createMap());
        p.setMapList(createMapList());
        p.setListMap(createListMap());
        p.setInfo(createInfo());
        p.setInfoList(createInfoList());
        p.setInfoMap(createInfoMap());
        p.setText("fgkfdgfdlg8df89g8fd9g89fd99999999999gklal;l;d;lasd7as7d5asd5as56ddddddas56ddddd5s6ad");

        p.setMapListInfo(createMapListInfo());
       // p.setInfoMap2(createInfoMap2()); //xml not support this type

        testJson(new TypeToken<Person>(){}, p);
        testYaml(new TypeToken<Person>(){}, p);
        testXml(new TypeToken<Person>(){}, p);
    }

    //for xml. only support self-object. so this method doesn't support xml.
    @Test
    public void testMap()throws Exception{
        Map<String, Info> map = createInfoMap();
        TypeToken<Map<String, Info>> tt = new TypeToken<Map<String, Info>>() {
        };
        testJson(tt, map);
        testYaml(tt, map);
    }
    //for xml. only support self-object. so this method doesn't support xml.
    @Test
    public void testListMap() throws Exception{
        List<Map<String, Integer>> list = createListMap();
        TypeToken<List<Map<String, Integer>>> tt = new TypeToken<List<Map<String, Integer>>>() {
        };
        testJson(tt, list);
        testYaml(tt, list);
    }

    private Map<Info, String> createInfoMap2() {
        Map<Info, String> map = new HashMap<>();
        map.put(createInfo(1), "value1");
        map.put(createInfo(2), "value2");
        map.put(createInfo(3), "value3");
        return map;
    }

    private Map<String, List<Info>> createMapListInfo() {
        Map<String, List<Info>> map = new HashMap<>();
        map.put("key1", Arrays.asList(createInfo()));
        map.put("key2", Arrays.asList(createInfo(), createInfo(), createInfo()));
        return map;
    }

    private void testXml(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .xml()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .xml()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    private void testJson(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .json()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .json()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    private void testYaml(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }

    private Map<String, Info> createInfoMap() {
        Map<String, Info> map = new HashMap<>();
        map.put("info1", createInfo());
        map.put("info2", createInfo());
        map.put("info3", createInfo());
        return map;
    }

    private List<Info> createInfoList() {
        return new ArrayList<>(Arrays.asList(createInfo(), createInfo()));
    }

    private Info createInfo() {
        Info info = new Info();
        info.setAddr("addr1");
        info.setPhone("12345");
        return info;
    }
    private Info createInfo(int index) {
        Info info = new Info();
        info.setAddr("addr__" + index);
        info.setPhone("phone__" + index);
        return info;
    }

    private List<Map<String, Integer>> createListMap() {
        List<Map<String, Integer>> list = new ArrayList<>();
        list.add(createMap());
        list.add(createMap());
        return list;
    }

    private Map<String, List<Integer>> createMapList() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("h1", Arrays.asList(1,2,3));
        map.put("h2", Arrays.asList(4,5,6));
        return map;
    }

    private Map<String, Integer> createMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("h1", 1);
        map.put("h2", 2);
        map.put("h3", 3);
        return map;
    }

    private List<String> createList() {
        return new ArrayList<>(Arrays.asList("h123", "h456", "h789"));
    }
}
