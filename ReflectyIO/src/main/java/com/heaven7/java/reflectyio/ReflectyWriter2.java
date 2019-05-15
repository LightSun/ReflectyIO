package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.ReflectyContext;

/**
 * the object write monitor.
 * @author heaven7
 * @since 1.0.5
 */
public interface ReflectyWriter2 extends ReflectyWriter{

    /**
     * called on begin write object
     * @param context the context
     * @param defineClass the defined class of field or method
     * @param obj the object to write
     */
    void beginWriteObject(ReflectyContext context, Class<?> defineClass, Object obj);

    /**
     * called on end write object
     */
    void endWriteObject();

    /**
     * called on start write member proxy. if version not match. this never called.
     * @param context the context
     * @param proxy the member proxy
     */
    void beginWriteMemberProxy(ReflectyContext context, MemberProxy proxy);
    /**
     * called on end write member proxy. if version not match. this never called.
     */
    void endWriteMemberProxy();
}
