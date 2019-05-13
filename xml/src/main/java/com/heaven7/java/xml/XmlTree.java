package com.heaven7.java.xml;

import com.heaven7.java.reflecty.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*public*/ class XmlTree implements Tree{

    List<XmlTree> children = new ArrayList<>();
    XmlTree parent;
    int childIndex = -1;
    XmlReaderImpl.Element element;

    int attibuteIndex = -1;
    final List<Attribute> attrList;
    boolean attributeEnd;

    public XmlTree(){
        attrList = null;
    }
    public XmlTree(XmlReaderImpl.Element element) {
        this.element = element;
        if(element.getAttributes() != null){
            attrList = new ArrayList<>();
            for (Map.Entry<String,String> en : element.getAttributes().entrySet()){
                attrList.add(new Attribute(this, en.getKey(), en.getValue()));
            }
        }else {
            attrList = null;
        }
    }
    public void addChild(XmlTree gl) {
        children.add(gl);
        gl.parent = this;
    }
    @Override
    public boolean hasNextChild() {
        if(attributeEnd){
            return children.size() > childIndex + 1;
        }else {
            return attrList.size() > attibuteIndex + 1;
        }
    }
    @Override
    public Tree nextChild(){
        if(attributeEnd){
            childIndex += 1;
            if(children.size() > childIndex){
                return children.get(childIndex);
            }
        }else {
            attibuteIndex += 1;
            attributeEnd = attibuteIndex == attrList.size() - 1;
            if(attrList.size() > attibuteIndex){
                return attrList.get(attibuteIndex);
            }
        }
        return null;
    }
    @Override
    public Tree getParent() {
        return parent;
    }
    @Override
    public String getBodyText() throws IOException {
        return element.getText();
    }
    @Override
    public String getName() throws IOException {
        return element.getName();
    }
    @Override
    public String getValue() {
        throw new UnsupportedOperationException();
    }

    static class Attribute extends Pair<String, String> implements Tree{

        final XmlTree parent;
        final List<StringTree> list = new ArrayList<>();
        int index = -1;

        public Attribute(XmlTree parent, String key, String value) {
            super(key, value);
            this.parent = parent;
            for (String str : value.split(",")){
                list.add(new StringTree(this, str));
            }
        }
        @Override
        public boolean hasNextChild() {
            return list.size() > index + 1;
        }
        @Override
        public Tree nextChild() {
            return list.get(++index);
        }
        @Override
        public Tree getParent() {
            return parent;
        }
        @Override
        public String getBodyText() throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public String getName() throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public String getValue() {
            throw new UnsupportedOperationException();
        }
    }
    static class StringTree implements Tree{
        final Tree parent;
        String value;

        public StringTree(Tree parent, String value) {
            this.parent = parent;
            this.value = value;
        }
        @Override
        public boolean hasNextChild() {
            return false;
        }
        @Override
        public Tree nextChild() {
            return null;
        }
        @Override
        public Tree getParent() {
            return parent;
        }
        @Override
        public String getBodyText() throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public String getName() throws IOException {
            throw new UnsupportedOperationException();
        }
        @Override
        public String getValue() {
            return value;
        }
    }

}
