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

/**
 * the yaml line
 * @author heaven7
 */
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
        //for annotate line. just return null.
        if(trim.startsWith("#")){
            return null;
        }
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