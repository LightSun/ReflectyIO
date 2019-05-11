package com.heaven7.java.yaml.entity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Person {

    private int age;
    private String name;
    private List<String> list;
    private Map<String, Integer> map;
    private List<Map<String, Integer>> listMap;
    private Map<String, List<Integer>> mapList;

    private List<Info> infoList;
    private Map<String, Info> infoMap;
    private Info info;

    public List<Info> getInfoList() {
        return infoList;
    }
    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

    public Map<String, Info> getInfoMap() {
        return infoMap;
    }
    public void setInfoMap(Map<String, Info> infoMap) {
        this.infoMap = infoMap;
    }

    public Info getInfo() {
        return info;
    }
    public void setInfo(Info info) {
        this.info = info;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(list, person.list) &&
                Objects.equals(map, person.map) &&
                Objects.equals(listMap, person.listMap) &&
                Objects.equals(mapList, person.mapList) &&
                Objects.equals(infoList, person.infoList) &&
                Objects.equals(infoMap, person.infoMap) &&
                Objects.equals(info, person.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, list, map, listMap, mapList, infoList, infoMap, info);
    }
}
