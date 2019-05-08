package com.heaven7.java.reflectyio.yaml;


class YamlLine {

    static final byte TYPE_PAIR  = 1;
    static final byte TYPE_NAME  = 2;       //array, or map or object member name.
    static final byte TYPE_ARRAY = 3;       // marked by '-'
    static final byte TYPE_ARRAY_EMPTY = 4; //an array and nest type.

    int indentCount;
    String name;
    String value;
    byte type;
    int index; // line index

    public static YamlLine parse(String line){
        YamlLine yl = new YamlLine();
        final String trim = line.trim();
        int index = line.indexOf(trim);
        if(trim.startsWith("-")){
            yl.type = trim.length() == 1 ? TYPE_ARRAY_EMPTY :TYPE_ARRAY;
            String str = trim.substring(1).trim();
            if(!str.isEmpty()){
                yl.value = str;
            }
        }else {
            int colonIndex = line.indexOf(":");
            yl.name = line.substring(index, colonIndex);
            String strLeft = line.substring(colonIndex + 1).trim();
            if(strLeft.isEmpty()){
                yl.type = TYPE_NAME;
            }else {
                yl.type = TYPE_PAIR;
                yl.value = strLeft;
            }
        }
        //the index of trim result is indent count.
        yl.indentCount = index;
        return yl;
    }

    @Override
    public String toString() {
        return "YamlLine{" +
                //"indentCount=" + indentCount +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + getTypeString(type) +
               // ", index=" + index +
                '}';
    }

    private static String getTypeString(byte type) {
        switch (type){
            case TYPE_ARRAY:
                return "TYPE_ARRAY";

            case TYPE_ARRAY_EMPTY:
                return "TYPE_ARRAY_EMPTY";
            case TYPE_NAME:
                return "TYPE_NAME";
            case TYPE_PAIR:
                return "TYPE_PAIR";
        }
        return null;
    }
}