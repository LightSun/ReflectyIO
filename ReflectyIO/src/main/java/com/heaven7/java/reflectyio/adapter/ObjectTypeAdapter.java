package com.heaven7.java.reflectyio.adapter;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.Reflecty;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflecty.iota.TypeAdapter;
import com.heaven7.java.reflectyio.ReflectyEvaluator;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;
import com.heaven7.java.reflectyio.StartEndMemberProxy;

import java.io.IOException;
import java.util.List;

import static com.heaven7.java.reflecty.utils.IotaUtils.getTypeAdapter;

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
            List<MemberProxy> proxies = mReflecty.getMemberProxies(mClazz);
            sink.beginObject(mClazz);
            try {
                for (MemberProxy proxy : proxies){
                    if(((StartEndMemberProxy)proxy).isVersionMatched(mApplyVersion)){
                        sink.name(proxy.getPropertyName());
                        getTypeAdapter(proxy.getTypeNode(), mTAM, mApplyVersion).write(sink, proxy.getValue(obj));
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e);
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
        source.beginObject(mClazz);
        try {
            while (source.hasNext()){
                MemberProxy proxy = findMemberProxy(proxies, source.nextName());
                if(proxy != null && ((StartEndMemberProxy) proxy).isVersionMatched(mApplyVersion)){
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
