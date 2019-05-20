package com.heaven7.java.yaml;

import com.heaven7.java.reflecty.TypeToken;
import com.heaven7.java.reflectyio.ReflectyIo;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.yaml.entity.Info;
import com.heaven7.java.yaml.entity.Person;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class YamlTest {

    private final StringWriter sw = new StringWriter();
    private final YamlWriter yamlWriter = new YamlWriter(sw);

    private void clean(){
        StringBuffer buffer = sw.getBuffer();
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

        testImpl(new TypeToken<Person>(){}, p);
        clean();
    }

    @Test
    public void testMap()throws Exception{
        Map<String, Info> map = createInfoMap();
        TypeToken<Map<String, Info>> tt = new TypeToken<Map<String, Info>>() {
        };
        testImpl(tt, map);
    }

    @Test
    public void testListMap() throws Exception{
        List<Map<String, Integer>> list = createListMap();
        TypeToken<List<Map<String, Integer>>> tt = new TypeToken<List<Map<String, Integer>>>() {
        };
        testImpl(tt, list);
    }

    private void testImpl(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo().delegate(new SimpleReflectyDelegate())
                .typeToken(tt)
                .build().write(yamlWriter, raw);
        System.out.println(sw.toString());

        YamlReader reader = new YamlReader(new StringReader(sw.toString()));
        Object obj = new ReflectyIo().delegate(new SimpleReflectyDelegate())
                .typeToken(tt)
                .build().read(reader);
        clean();

        Assert.assertEquals(obj, raw);
    }

    private Map<String, Info> createInfoMap() {
        Map<String, Info> map = new HashMap<>();
        map.put("info1", createInfo());
        map.put("info2", createInfo());
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
