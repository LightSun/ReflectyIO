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
import com.heaven7.java.reflectyio.plugin.ReflectyPlugin;
import com.heaven7.java.reflectyio.plugin.ReflectyPluginManager;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * the helper class used to quick serialize and deserialize data.
 * @author heaven7
 */
public final class ReflectyIo {

    public static final int PLUGIN_TYPE_XML   = 1;
    public static final int PLUGIN_TYPE_YAML  = 2;
    public static final int PLUGIN_TYPE_JSON  = 3;

    private ReflectyEvaluator evaluator;
    private ReflectyContext context;
    private Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty;

    private ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam;
    private Type type;
    private float version = 1.0f;

    private TypeAdapter<ReflectyWriter, ReflectyReader> adapter;
    private ReflectyPlugin plugin;

    /**
     * set the Reflecty Evaluator
     * @param evaluator the Reflecty Evaluator
     * @return this
     */
    public ReflectyIo evaluator(ReflectyEvaluator evaluator) {
        this.evaluator = evaluator;
        return this;
    }

    /**
     * set the reflecty context
     * @param context the reflecty context
     * @return this
     */
    public ReflectyIo context(ReflectyContext context) {
        this.context = context;
        return this;
    }

    /**
     * set the reflecty
     * @param reflecty the reflecty
     * @return this
     */
    public ReflectyIo reflecty(Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty) {
        this.reflecty = reflecty;
        return this;
    }

    /**
     * set the reflecty delegate
     * @param delegate the delegate
     * @return this
     */
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

    /**
     * set the type adapter manager.
     * @param tam the type adapter manager
     * @return this
     */
    public ReflectyIo tam(ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam) {
        this.tam = tam;
        return this;
    }

    /**
     * assign the plugin to create {@linkplain ITypeAdapterManager}.
     * @param type the type
     * @return this
     * @see ReflectyPlugin
     * @since 1.0.6
     */
    public ReflectyIo pluginType(int type){
        this.plugin = ReflectyPluginManager.getDefault().getReflectyPlugin(type);
        return this;
    }

    /**
     * easy way to assign plugin type is xml.
     * @return this
     * @since 1.0.7
     */
    public ReflectyIo xml(){
        return pluginType(PLUGIN_TYPE_XML);
    }
    /**
     * easy way to assign plugin type is yaml.
     * @return this
     * @since 1.0.7
     */
    public ReflectyIo yaml(){
        return pluginType(PLUGIN_TYPE_YAML);
    }
    /**
     * easy way to assign plugin type is json.
     * @return this
     * @since 1.0.7
     */
    public ReflectyIo json(){
        return pluginType(PLUGIN_TYPE_JSON);
    }

    /**
     * assign the type which used to write or read object.
     * <p>for read you must call this. that means you should call this method or {@linkplain #typeToken(TypeToken)} before {@linkplain #read(ReflectyReader)}.</p>
     * <p>for write. if the object is not generic. you can just ignore call this method and  {@linkplain #typeToken(TypeToken)}. or else you
     * must call this method or {@linkplain #typeToken(TypeToken)} before {@linkplain #read(ReflectyReader)}. </p>
     * @param type the type.
     * @return this
     */
    public ReflectyIo type(Type type){
        this.type = type;
        return this;
    }

    /**
     * assign the type which used to write or read object.
     * <p>for read you must call this. that means you should call this method or {@linkplain #type(Type)} before {@linkplain #read(ReflectyReader)}.</p>
     * <p>for write. if the object is not generic. you can just ignore call this method and  {@linkplain #type(Type)} . or else you
     * must call this method or {@linkplain #type(Type)}  before {@linkplain #read(ReflectyReader)}. </p>
     * @param tt the type token.
     * @return this
     */
    public ReflectyIo typeToken(TypeToken<?> tt){
        this.type = tt.getType();
        return this;
    }

    /**
     * set the version which can used to do with object-compat.
     * @param version the version
     * @return this
     */
    public ReflectyIo version(float version){
        this.version = version;
        if(version <= 0 ){
            throw new IllegalArgumentException();
        }
        return this;
    }

    /**
     * build type adapter. this often called before {@linkplain #write(ReflectyWriter, Object)} and {@linkplain #read(ReflectyReader)}.
     * @return this
     */
    public ReflectyIo build(){
        buildTamInternal();
        if(type != null){
            adapter = TypeAdapter.ofType(type, tam, version);
        }
        return this;
    }

    /**
     * register the type adapter. this method must be called after {@linkplain #build()} or else must cause {@linkplain NullPointerException}.
     * @param type the type
     * @param version the version which used for compat . default will set to 1.0f.
     * @param adapter the type adapter you want
     * @return this
     * @since 1.0.8
     */
    public ReflectyIo registerTypeAdapter(Type type, float version, TypeAdapter<ReflectyWriter, ReflectyReader> adapter){
        tam.registerTypeAdapter(type, version, adapter);
        return this;
    }
    /**
     * register the type adapter. and use the default version which is from {@linkplain #version(float)}. if not set default is 1.0f.
     * this method must be called after {@linkplain #build()} or else must cause {@linkplain NullPointerException}.
     * @param type the type
     * @param adapter the type adapter you want
     * @return this
     * @since 1.0.8
     */
    public ReflectyIo registerTypeAdapter(Type type, TypeAdapter<ReflectyWriter, ReflectyReader> adapter){
        tam.registerTypeAdapter(type, version, adapter);
        return this;
    }

    /**
     * write the object by the writer
     * @param writer the writer
     * @param obj the object to write
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * write the object by the writer
     * @param writer1 the writer
     * @param obj the object to write
     * @throws IOException if an I/O error occurs.
     * @since 1.0.7
     */
    public void write(Writer writer1, Object obj) throws IOException{
        if(plugin == null){
            throw new IllegalStateException("you must call #pluginType() first.");
        }
        write(plugin.createReflectyWriter(writer1), obj);
    }

    /**
     * write the object by the writer without IOException.
     * @param writer the writer
     * @param obj the object to write
     * @since 1.0.7
     */
    public void write2(Writer writer, Object obj){
        try {
            write(writer, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * write the object by the writer without IOException.
     * @param writer the writer
     * @param obj the object to write
     * @since 1.0.6
     */
    public void write2(ReflectyWriter writer, Object obj){
        try {
            write(writer, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * read object. for read. you must call {@linkplain #type(Type)} or {@linkplain TypeToken} before this.
     * @param reader the reader
     * @param <T> the type you want to read.
     * @return the object
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * read object. for read. you must call {@linkplain #type(Type)} or {@linkplain TypeToken} before this.
     * @param reader the reader
     * @param <T> the type you want to read.
     * @return the object
     * @throws IOException if an I/O error occurs.
     * @since 1.0.7
     */
    @SuppressWarnings("unchecked")
    public <T> T read(Reader reader) throws IOException{
        if(plugin == null){
            throw new IllegalStateException("you must call #pluginType() first.");
        }
        return read(plugin.createReflectyReader(reader));
    }

    /**
     * read object without IOException. for read. you must call {@linkplain #type(Type)} or {@linkplain TypeToken} before this.
     * @param reader the reader
     * @param <T> the type you want to read.
     * @return the object
     * @since 1.0.6
     */
    @SuppressWarnings("unchecked")
    public <T> T read2(ReflectyReader reader){
        try {
            return read(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * read object without IOException. for read. you must call {@linkplain #type(Type)} or {@linkplain TypeToken} before this.
     * @param reader the reader
     * @param <T> the type you want to read.
     * @return the object
     * @since 1.0.7
     */
    @SuppressWarnings("unchecked")
    public <T> T read2(Reader reader){
        try {
            return read(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buildTamInternal() {
        if(plugin != null){
            tam = plugin.createTypeAdapterManager();
        }
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
