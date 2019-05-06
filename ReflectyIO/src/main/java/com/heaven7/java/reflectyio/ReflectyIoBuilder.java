package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.*;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyInherit;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;

import java.io.IOException;
import java.lang.reflect.Type;

public final class ReflectyIoBuilder {

    private static volatile ITypeAdapterManager<ReflectyWriter, ReflectyReader> sTAM;

    private ReflectyEvaluator evaluator;
    private ReflectyContext context;
    private Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty;

    private ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam;
    private Type type;
    private float version = 1.0f;

    private TypeAdapter<ReflectyWriter, ReflectyReader> adapter;

    public ReflectyIoBuilder evaluator(ReflectyEvaluator evaluator) {
        this.evaluator = evaluator;
        return this;
    }
    public ReflectyIoBuilder context(ReflectyContext context) {
        this.context = context;
        return this;
    }
    public ReflectyIoBuilder reflecty(Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> reflecty) {
        this.reflecty = reflecty;
        return this;
    }
    public ReflectyIoBuilder delegate(ReflectyDelegate<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> delegate) {
        this.reflecty = new ReflectyBuilder<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit>()
                .classAnnotation(ReflectyClass.class)
                .fieldAnnotation(ReflectyField.class)
                .methodAnnotation(ReflectyMethod.class)
                .inheritAnnotation(ReflectyInherit.class)
                .delegate(delegate)
                .build();
        return this;
    }
    public ReflectyIoBuilder tam(ITypeAdapterManager<ReflectyWriter, ReflectyReader> tam) {
        this.tam = tam;
        return this;
    }

    public ReflectyIoBuilder cacheTAM(){
        buildTamInternal();
        sTAM = this.tam;
        return this;
    }
    public ReflectyIoBuilder useCacheTAM(){
        this.tam = sTAM;
        return this;
    }

    public ReflectyIoBuilder type(Type type){
        this.type = type;
        return this;
    }
    public ReflectyIoBuilder typeToken(TypeToken<?> tt){
        this.type = tt.getType();
        return this;
    }
    public ReflectyIoBuilder version(float version){
        this.version = version;
        if(version <= 0 ){
            throw new IllegalArgumentException();
        }
        return this;
    }
    public ReflectyIoBuilder build(){
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
        adapter.write(writer, obj);
    }
    @SuppressWarnings("unchecked")
    public <T> T read(ReflectyReader reader) throws IOException{
        if(tam == null){
            throw new IllegalStateException("you must call method '#build()' first.");
        }
        if(type == null){
            throw new IllegalStateException("for read. type can't be null");
        }
        return (T) adapter.read(reader);
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
                evaluator = new SimpleReflectyEvaluator();
            }
            tam = new ReflectyTypeAdapterManager(context, evaluator, reflecty);
        }
    }
}
