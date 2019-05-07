package com.heaven7.java.reflectyio.yaml.entity;

import java.util.List;
import java.util.Map;

public class Person {

    private int age;
    private String name;
    private List<String> list;
    private Map<String, Integer> map;
    private List<Map<String, Integer>> listMap;
    private Map<String, List<Integer>> mapList;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }
    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Integer> getMap() {
        return map;
    }
    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public List<Map<String, Integer>> getListMap() {
        return listMap;
    }
    public void setListMap(List<Map<String, Integer>> listMap) {
        this.listMap = listMap;
    }

    public Map<String, List<Integer>> getMapList() {
        return mapList;
    }
    public void setMapList(Map<String, List<Integer>> mapList) {
        this.mapList = mapList;
    }
}
