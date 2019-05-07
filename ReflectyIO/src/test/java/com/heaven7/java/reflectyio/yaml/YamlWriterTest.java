package com.heaven7.java.reflectyio.yaml;

import com.heaven7.java.reflectyio.ReflectyIo;
import com.heaven7.java.reflectyio.SimpleReflectyDelegate;
import com.heaven7.java.reflectyio.yaml.entity.Person;
import org.junit.Test;

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

        new ReflectyIo().delegate(new SimpleReflectyDelegate())
                .build().cacheTAM().write(yamlWriter, p);
        System.out.println(sw.toString());
        clean();
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
