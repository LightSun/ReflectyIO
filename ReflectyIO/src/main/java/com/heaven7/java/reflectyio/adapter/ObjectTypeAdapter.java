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
import com.heaven7.java.reflectyio.*;

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
    /** the field indicate the the output and input should have no name. that means only have value. there is no mapping name.
     * @since 1.0.1
     * */
    private boolean mNoMappingName;

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

    /**
     * indicate the read source will have no mapping name or not
     * @return false if has no mapping name. true otherwise.
     * @since 1.0.1
     */
    public boolean isNoMappingName() {
        return mNoMappingName;
    }

    /**
     * set that the read source will have no mapping name or not. default is false means has mapping name.
     * @param mNoMappingName  false means has mapping name
     * @since 1.0.1
     */
    public void setNoMappingName(boolean mNoMappingName) {
        this.mNoMappingName = mNoMappingName;
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
                            TypeAdapter<ReflectyWriter, ReflectyReader> ta2 = getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion);
                            if(proxy instanceof Commissioner){
                                ((Commissioner) proxy).write(ta2, sink, proxy.getValue(obj));
                            }else {
                                sink.name(proxy.getPropertyName());
                                ta2.write(sink, proxy.getValue(obj));
                            }
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
            if(!mNoMappingName){
                while (source.hasNext()){
                    MemberProxy proxy = findMemberProxy(proxies, source.nextName());
                    if(proxy != null && ((VersionMemberProxy) proxy).isVersionMatched(mApplyVersion)){
                        TypeAdapter<ReflectyWriter, ReflectyReader> ta2 = getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion);
                        if(proxy instanceof Commissioner){
                            ((Commissioner) proxy).read(ta2, source);
                        }else {
                            proxy.setValue(obj, ta2.read(source));
                        }
                    }else {
                        source.skipValue();
                    }
                }
            }else {
                for (MemberProxy proxy : proxies){
                    if(((VersionMemberProxy) proxy).isVersionMatched(mApplyVersion)){
                        TypeAdapter<ReflectyWriter, ReflectyReader> ta2 = getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion);
                        if(proxy instanceof Commissioner){
                            ((Commissioner) proxy).read(ta2, source);
                        }else {
                            proxy.setValue(obj, ta2.read(source));
                        }
                    }else {
                        source.skipValue();
                    }
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
