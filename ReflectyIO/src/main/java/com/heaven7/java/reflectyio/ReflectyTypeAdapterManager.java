package com.heaven7.java.reflectyio;

import com.heaven7.java.base.anno.NonNull;
import com.heaven7.java.base.util.Throwables;
import com.heaven7.java.reflecty.Reflecty;
import com.heaven7.java.reflecty.ReflectyBuilder;
import com.heaven7.java.reflecty.ReflectyContext;
import com.heaven7.java.reflecty.SimpleReflectyContext;
import com.heaven7.java.reflecty.iota.AbstractTypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.adapter.*;
import com.heaven7.java.reflectyio.anno.ReflectyClass;
import com.heaven7.java.reflectyio.anno.ReflectyField;
import com.heaven7.java.reflectyio.anno.ReflectyInherit;
import com.heaven7.java.reflectyio.anno.ReflectyMethod;

/**
 * @author  heaven7
 */
public final class ReflectyTypeAdapterManager extends AbstractTypeAdapterManager<ReflectyWriter, ReflectyReader>{

    private final Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty;
    private final ReflectyEvaluator evaluator;

    public ReflectyTypeAdapterManager(){
        this(new ReflectyBuilder<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit>()
                .classAnnotation(ReflectyClass.class)
                .fieldAnnotation(ReflectyField.class)
                .methodAnnotation(ReflectyMethod.class)
                .inheritAnnotation(ReflectyInherit.class)
                .delegate(new SimpleReflectyDelegate())
                .build());
    }
    public ReflectyTypeAdapterManager(Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty) {
        this(new SimpleReflectyContext(), SimpleReflectyEvaluator.INSTANCE, mReflecty);
    }
    public ReflectyTypeAdapterManager(@NonNull ReflectyContext context, @NonNull ReflectyEvaluator evaluator,
                                      @NonNull Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty) {
        super(context);
        Throwables.checkNull(context);
        Throwables.checkNull(evaluator);
        Throwables.checkNull(mReflecty);
        this.evaluator = evaluator;
        this.mReflecty = mReflecty;
        registerCore();
    }

    private void registerCore() {
        registerAdapters(byte.class, Byte.class);
        registerAdapters(short.class, Short.class);
        registerAdapters(int.class, Integer.class);
        registerAdapters(long.class, Long.class);
        registerAdapters(float.class, Float.class);
        registerAdapters(double.class, Double.class);
        registerAdapters(boolean.class, Boolean.class);

        CharAdapter ca = new CharAdapter(evaluator);
        registerBasicTypeAdapter(char.class, ca);
        registerBasicTypeAdapter(Character.class, ca);

        registerBasicTypeAdapter(String.class, new StringAdapter(evaluator));
    }

    private <T> void registerAdapters(Class<?> base, Class<T> wrap){
        CommonTypeAdapter<T> byteAdapter = new CommonTypeAdapter<T>(evaluator, wrap);
        registerBasicTypeAdapter(base, byteAdapter);
        registerBasicTypeAdapter(wrap, byteAdapter);
    }

    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createCollectionTypeAdapter(Class<?> collectionClass, TypeAdapter<ReflectyWriter, ReflectyReader> componentAdapter) {
        return new CollectionTypeAdapter(evaluator, getReflectyContext(), collectionClass, componentAdapter);
    }
    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createArrayTypeAdapter(Class<?> componentClass, TypeAdapter<ReflectyWriter, ReflectyReader> componentAdapter) {
        return new ArrayTypeAdapter(evaluator, componentClass, componentAdapter);
    }
    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createMapTypeAdapter(Class<?> mapClazz, TypeAdapter<ReflectyWriter, ReflectyReader> keyAdapter,
                                                                            TypeAdapter<ReflectyWriter, ReflectyReader> valueAdapter) {
        return new MapTypeAdapter(evaluator, getReflectyContext(), mapClazz, keyAdapter, valueAdapter);
    }

    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createObjectTypeAdapter(Class<?> objectClazz, float applyVersion) {
        return new ObjectTypeAdapter(evaluator, mReflecty, this, objectClazz, applyVersion);
    }
}
