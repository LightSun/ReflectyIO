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

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.Reflecty;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;
import com.heaven7.java.reflectyio.adapter.ObjectTypeAdapter;

import java.util.List;

/**
 * the xml object type adapter.
 * @author heaven7
 */
/*public*/ class XmlObjectTypeAdapter extends ObjectTypeAdapter{

    public XmlObjectTypeAdapter(ReflectyEvaluator evaluator,
                                Reflecty<TypeAdapter<ReflectyWriter, ReflectyReader>, ?, ?, ?, ?> reflecty, ITypeAdapterManager<ReflectyWriter, ReflectyReader> mTAM,
                                Class<?> mClazz, float mApplyVersion) {
        super(evaluator, reflecty, mTAM, mClazz, mApplyVersion);
    }

    @Override
    protected MemberProxy findMemberProxy(List<MemberProxy> proxies, String name) {
        if(!XmlTree.NAME_BODY.equals(name)){
            return super.findMemberProxy(proxies, name);
        }else {
            for (MemberProxy proxy : proxies){
                if(proxy instanceof XmlBodyProxy){
                    return proxy;
                }
            }
            return null;
        }
    }
}
