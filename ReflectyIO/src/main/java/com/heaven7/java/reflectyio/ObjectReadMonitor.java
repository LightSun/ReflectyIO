package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.ReflectyContext;

/**
 * the object read monitor . which used when read self-object. that means non-collection or non-map.
 * @author heaven7
 * @since 1.0.5
 */
public interface ObjectReadMonitor {

    /**
     * called on begin read object
     * @param context the context
     * @param defineClass the defined class of field or method
     */
    void beginReadObject(ReflectyContext context, Class<?> defineClass);

    /**
     * called on end read object
     */
    void endReadObject();

    /**
     * called on start read member proxy. if version not match. this never called.
     * @param context the context
     * @param proxy the member proxy
     */
    void beginReadMemberProxy(ReflectyContext context, MemberProxy proxy);
    /**
     * called on end read member proxy. if version not match. this never called.
     */
    void endReadMemberProxy();
}
