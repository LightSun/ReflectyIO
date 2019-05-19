package com.heaven7.java.xml.entity;

import com.heaven7.java.xml.XmlBody;
import com.heaven7.java.xml.XmlElement;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@XmlElement("Google")
public class Person {

    private int age;
    private String name;
    private List<String> list;
    private Map<String, Integer> map;
    @XmlElement("list_map")
    private List<Map<String, Integer>> listMap; //用注解，映射child element name.
    private Map<String, List<Integer>> mapList;
    private Map<String, List<Info>> mapListInfo;

    private  List<Info> infoList;
    private  Map<String, Info> infoMap;
    private transient Map<Info, String> infoMap2; //this is not support for xml serialize/deserialize. so add transient
    private  Info info;

    @XmlBody
    private String text;

    public Map<Info, String> getInfoMap2() {
        return infoMap2;
    }
    public void setInfoMap2(Map<Info, String> infoMap2) {
        this.infoMap2 = infoMap2;
    }

    public Map<String, List<Info>> getMapListInfo() {
        return mapListInfo;
    }
    public void setMapListInfo(Map<String, List<Info>> mapListInfo) {
        this.mapListInfo = mapListInfo;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

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
