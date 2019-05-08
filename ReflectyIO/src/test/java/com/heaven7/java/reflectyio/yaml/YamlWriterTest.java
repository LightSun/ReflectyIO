package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.reflectyio.ReflectyIo;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.reflectyio.yaml.entity.Info;
import com.heaven7.java.reflectyio.yaml.entity.Person;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

public class YamlWriterTest {

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

        new ReflectyIo().delegate(new SimpleReflectyDelegate())
                .build().cacheTAM().write(yamlWriter, p);
        System.out.println(sw.toString());

        BufferedReader reader = new BufferedReader(new StringReader(sw.toString()));
        List<String> lines = new ArrayList<>();
        String line ;
        while ((line = reader.readLine()) != null){
            lines.add(line);
        }
        new YamlReader().analyzeLines(lines);
        clean();
    }

    private Map<String, Info> createInfoMap() {
        Map<String, Info> map = new HashMap<>();
        map.put("info1", createInfo());
        map.put("info2", createInfo());
        return map;
    }

    private List<Info> createInfoList() {
        return Arrays.asList(createInfo(), createInfo());
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
        return Arrays.asList("h123", "h456", "h789");
    }
}
