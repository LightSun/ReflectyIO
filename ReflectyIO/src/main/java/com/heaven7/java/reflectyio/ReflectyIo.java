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
package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.*;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyInherit;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;
import com.heaven7.java.reflectyio.plugin.ReflectyPluginManager;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * the helper class used to quick serialize and deserialize data.
 * @author heaven7
 */
public final class ReflectyIo {

    private ReflectyEvaluator evaluator;
    private ReflectyContext context;
    private Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty;

    private ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam;
    private Type type;
    private float version = 1.0f;

    private TypeAdapter<ReflectyWriter, ReflectyReader> adapter;

    public ReflectyIo evaluator(ReflectyEvaluator evaluator) {
        this.evaluator = evaluator;
        return this;
    }
    public ReflectyIo context(ReflectyContext context) {
        this.context = context;
        return this;
    }
    public ReflectyIo reflecty(Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty) {
        this.reflecty = reflecty;
        return this;
    }
    public ReflectyIo delegate(ReflectyDelegate<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> delegate) {
        this.reflecty = new ReflectyBuilder<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit>()
                .classAnnotation(ReflectyClass.class)
                .fieldAnnotation(ReflectyField.class)
                .methodAnnotation(ReflectyMethod.class)
                .inheritAnnotation(ReflectyInherit.class)
                .delegate(delegate)
                .build();
        return this;
    }
    public ReflectyIo tam(ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam) {
        this.tam = tam;
        return this;
    }

    /**
     * assign the plugin to create {@linkplain ITypeAdapterManager}.
     * @param type the type
     * @return this
     * @since 1.0.6
     */
    public ReflectyIo pluginType(int type){
        try {
            this.tam = ReflectyPluginManager.getDefault().getReflectyPlugin(type).createTypeAdapterManager();
        }catch (NullPointerException e){
            throw new RuntimeException("you should register reflecty plugin for type = " + type + " first. see @ReflectyPluginManager.");
        }
        return this;
    }

    public ReflectyIo type(Type type){
        this.type = type;
        return this;
    }
    public ReflectyIo typeToken(TypeToken<?> tt){
        this.type = tt.getType();
        return this;
    }
    public ReflectyIo version(float version){
        this.version = version;
        if(version <= 0 ){
            throw new IllegalArgumentException();
        }
        return this;
    }
    public ReflectyIo build(){
        buildTamInternal();
        if(type != null){
            adapter = TypeAdapter.ofType(type, tam, version);
        }
        return this;
    }

    public void write(ReflectyWriter writer, Object obj) throws IOException{
        if(tam == null){
            throw new IllegalStateException("you must call method '#build()' first.");
        }
        if(adapter == null){
            ReflectyContext context = tam.getReflectyContext();
            if(context.isCollection(obj.getClass()) || context.isMap(obj.getClass())){
                if(type == null){
                    throw new IllegalStateException("for collection/map , type can't be null");
                }
            }
            adapter = tam.createObjectTypeAdapter(obj.getClass(), version);
        }
        writer.begin(obj);
        adapter.write(writer, obj);
        writer.end(obj);
        writer.flush();
    }
    @SuppressWarnings("unchecked")
    public <T> T read(ReflectyReader reader) throws IOException{
        if(tam == null){
            throw new IllegalStateException("you must call method '#build()' first.");
        }
        if(type == null){
            throw new IllegalStateException("for read. type can't be null");
        }
        reader.begin();
        T result = (T) adapter.read(reader);
        reader.end();
        return result;
    }


    private void buildTamInternal() {
        if(tam == null){
            if(reflecty == null){
                throw new IllegalStateException("reflecty can't be null.");
            }
            if(context == null){
                context = new SimpleReflectyContext();
            }
            if(evaluator == null){
                evaluator = SimpleReflectyEvaluator.INSTANCE;
            }
            tam = new ReflectyTypeAdapterManager(context, evaluator, reflecty);
        }
    }
}
