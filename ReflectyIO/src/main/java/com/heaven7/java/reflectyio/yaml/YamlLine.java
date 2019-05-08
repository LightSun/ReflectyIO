package com.heaven7.java.reflectyio.yaml;


class YamlLine {

    static byte TYPE_PAIR  = 1;
    static byte TYPE_NAME  = 2;       //array, or map or object member name.
    static byte TYPE_ARRAY = 3;       // marked by '-'
    static byte TYPE_ARRAY_EMPTY = 4; //an array and nest type.

    int indentCount;
    String name;
    String value;
    byte type;
    int index; // line index

    static YamlLine parse(String line){
        YamlLine yl = new YamlLine();
        String trim = line.trim();
        if(trim.startsWith("-")){
            yl.type = trim.length() == 1 ? TYPE_ARRAY_EMPTY :TYPE_ARRAY;
        }else {
            int colonIndex = line.indexOf(":");
            if(line.substring(colonIndex + 1).trim().isEmpty()){
                yl.type = TYPE_NAME;
            }else {
                yl.type = TYPE_PAIR;
            }
        }
        //TODO parse others.
        return yl;
    }
}