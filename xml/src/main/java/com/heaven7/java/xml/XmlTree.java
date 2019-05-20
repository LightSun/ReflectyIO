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
package com.heaven7.java.xml;

import com.heaven7.java.base.util.Predicates;
import com.heaven7.java.reflecty.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * the xml tree
 * @author heaven7
 */
/*public*/ class XmlTree implements Tree{

    //TODO wait parse body by 'extend ObjectTypeAdapter'
    static final String NAME_BODY = XmlTree.class.getName() + "_@body@";

    List<XmlTree> children = new ArrayList<>();
    XmlTree parent;
    int childIndex = -1;
    XmlReaderImpl.Element element;

    int attibuteIndex = -1;
    final List<Attribute> attrList;
    boolean attributeEnd;

    String bodyText;

   /* public XmlTree(){
        attrList = null;
        hasBody = false;
    }*/
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
        this.bodyText = element.getText();
    }

    public void reset(){
        bodyText = element.getText();
        attributeEnd = false;
        attibuteIndex = -1;
        childIndex = -1;
        for (XmlTree tree : children){
            tree.reset();
        }
    }

    public void addChild(XmlTree gl) {
        children.add(gl);
        gl.parent = this;
    }
    @Override
    public boolean hasNextChild() {
        if(Predicates.isEmpty(attrList) || attributeEnd){
            if(children.size() > childIndex + 1){
                return true;
            }
            return bodyText != null;
        }else {
            return attrList.size() > attibuteIndex + 1;
        }
    }
    @Override
    public Tree nextChild(){
        if(Predicates.isEmpty(attrList) || attributeEnd){
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
        if(bodyText != null){
            StringTree tree = new StringTree(this, NAME_BODY, bodyText);
            bodyText = null;
            return tree;
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
                list.add(new StringTree(this, null, str));
            }
        }
        @Override
        public boolean hasNextChild() {
            return list.size() > index + 1;
        }
        @Override
        public Tree nextChild() {
            if(list.size() > index + 1){
                return list.get(++index);
            }
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
            return key;
        }
        @Override
        public String getValue() {
            return value;
        }
    }
    static class StringTree extends Pair<String, String> implements Tree{
        final Tree parent;

        public StringTree(Tree parent, String name, String value) {
            super(name, value);
            this.parent = parent;
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
            if(key == null){
                throw new UnsupportedOperationException();
            }
            return value;
        }
        @Override
        public String getName() throws IOException {
            if(key == null){
                throw new UnsupportedOperationException();
            }
            return key;
        }
        @Override
        public String getValue() {
            return value;
        }
    }

}
