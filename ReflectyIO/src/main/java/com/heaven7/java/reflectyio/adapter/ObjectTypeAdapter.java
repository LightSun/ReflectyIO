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
package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.Reflecty;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;
import com.heaven7.java.reflectyio.VersionMemberProxy;
import java.io.IOException;
import java.util.List;

import static com.heaven7.java.reflecty.utils.IotaUtils.getTypeAdapter;

/**
 * the object type adapter to read self-object(non-collection, non-map).
 * it use {@linkplain MemberProxy} to read and write data which is depend on runtime annotation.
 * @see MemberProxy
 * @see com.heaven7.java.reflecty.member.FieldProxy
 * @see com.heaven7.java.reflecty.member.MethodProxy
 * @author heaven7
 */
public class ObjectTypeAdapter extends AbstractTypeAdapter {

    private final Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ?, ?, ?, ?> mReflecty;
    private final ITypeAdapterManager<ReflectyWriter, ReflectyReader> mTAM;
    private final Class<?> mClazz;
    private final float mApplyVersion;

    public ObjectTypeAdapter(ReflectyEvaluator evaluator,
             Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ?, ?, ?, ?> reflecty,
                             ITypeAdapterManager<ReflectyWriter, ReflectyReader> mTAM,
                             Class<?> mClazz, float mApplyVersion) {
        super(evaluator);
        this.mReflecty = reflecty;
        this.mTAM = mTAM;
        this.mApplyVersion = mApplyVersion;
        this.mClazz = mClazz;
    }

    @Override
    public int write(ReflectyWriter sink, Object obj) throws IOException {
        //dynamic first moved to TypeNode
        //annotation second
        TypeAdapter<ReflectyWriter, ReflectyReader> ta = mReflecty.performReflectClass(mClazz);
        if(ta != null){
            ta.write(sink, obj);
        }else {
            //last is use member
            sink.beginObject(mTAM.getReflectyContext(), mClazz);
            if(obj != null) {
                List<MemberProxy> proxies = mReflecty.getMemberProxies(mClazz);
                try {
                    for (MemberProxy proxy : proxies) {
                        if (((VersionMemberProxy) proxy).isVersionMatched(mApplyVersion)) {
                            sink.name(proxy.getPropertyName());
                            getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion).write(sink, proxy.getValue(obj));
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            sink.endObject();
        }
        return 0;
    }

    @Override
    public Object read(ReflectyReader source) throws IOException {
        //1, dynamic first moved to TypeNode
        //2, runtime annotation
        TypeAdapter<ReflectyWriter, ReflectyReader> ta = mReflecty.performReflectClass(mClazz);
        if(ta != null){
            return ta.read(source);
        }

        Object obj = mTAM.getReflectyContext().newInstance(mClazz);
        List<MemberProxy> proxies = mReflecty.getMemberProxies(mClazz);
        source.beginObject(mTAM.getReflectyContext(),mClazz);
        try {
            while (source.hasNext()){
                MemberProxy proxy = findMemberProxy(proxies, source.nextName());
                if(proxy != null && ((VersionMemberProxy) proxy).isVersionMatched(mApplyVersion)){
                    Object value = getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion).read(source);
                    proxy.setValue(obj, value);
                }else {
                    source.skipValue();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        source.endObject();
        return obj;
    }

    private static MemberProxy findMemberProxy(List<MemberProxy> proxies, String name) {
        for (MemberProxy proxy : proxies){
            if(name.equals(proxy.getPropertyName())){
                return proxy;
            }
        }
        return null;
    }
}
