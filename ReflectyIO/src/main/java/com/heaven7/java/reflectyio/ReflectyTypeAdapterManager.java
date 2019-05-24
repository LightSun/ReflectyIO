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
public class ReflectyTypeAdapterManager extends AbstractTypeAdapterManager<ReflectyWriter, ReflectyReader>{

    private final Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> mReflecty;
    private final ReflectyEvaluator mEvaluator;

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
        this.mEvaluator = evaluator;
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
       // registerAdapters(boolean.class, Boolean.class);

        CharAdapter ca = new CharAdapter(mEvaluator);
        registerBasicTypeAdapter(char.class, ca);
        registerBasicTypeAdapter(Character.class, ca);

        BooleanAdapter ba = new BooleanAdapter(mEvaluator);
        registerBasicTypeAdapter(boolean.class, ba);
        registerBasicTypeAdapter(Boolean.class, ba);

        registerBasicTypeAdapter(String.class, new StringAdapter(mEvaluator));
    }

    private <T> void registerAdapters(Class<?> base, Class<T> wrap){
        CommonTypeAdapter<T> byteAdapter = new CommonTypeAdapter<T>(mEvaluator, wrap);
        registerBasicTypeAdapter(base, byteAdapter);
        registerBasicTypeAdapter(wrap, byteAdapter);
    }

    /**
     * get the reflecty
     * @return the reflecty
     * @since 1.0.7
     */
    public Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ReflectyClass, ReflectyField, ReflectyMethod, ReflectyInherit> getReflecty(){
        return mReflecty;
    }

    /**
     * get reflecty evaluator
     * @return the reflecty evaluator
     * @since 1.0.7
     */
    public ReflectyEvaluator getReflectyEvaluator(){
        return mEvaluator;
    }

    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createCollectionTypeAdapter(Class<?> collectionClass, TypeAdapter<ReflectyWriter, ReflectyReader> componentAdapter) {
        return new CollectionTypeAdapter(mEvaluator, getReflectyContext(), collectionClass, componentAdapter);
    }
    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createArrayTypeAdapter(Class<?> componentClass, TypeAdapter<ReflectyWriter, ReflectyReader> componentAdapter) {
        return new ArrayTypeAdapter(mEvaluator, componentClass, componentAdapter);
    }
    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createMapTypeAdapter(Class<?> mapClazz, TypeAdapter<ReflectyWriter, ReflectyReader> keyAdapter,
                                                                            TypeAdapter<ReflectyWriter, ReflectyReader> valueAdapter) {
        return new MapTypeAdapter(mEvaluator, getReflectyContext(), mapClazz, keyAdapter, valueAdapter);
    }

    @Override
    public TypeAdapter<ReflectyWriter, ReflectyReader> createObjectTypeAdapter(Class<?> objectClazz, float applyVersion) {
        return new ObjectTypeAdapter(mEvaluator, mReflecty, this, objectClazz, applyVersion);
    }
}
