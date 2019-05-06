package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.member.MethodProxy;
import com.heaven7.java.reflectyio.anno.Since;
import com.heaven7.java.reflectyio.anno.Until;

import java.lang.reflect.Method;

public class ReflectyMethodProxy extends MethodProxy implements StartEndMemberProxy{

    private final float since;
    private final float until;

    public ReflectyMethodProxy(Class<?> ownerClass, Method get, Method set, String property) {
        super(ownerClass, get, set, property);

        Since u = get.getAnnotation(Since.class);
        Until n = get.getAnnotation(Until.class);
        this.since = u != null ? u.value() : -1f;
        this.until = n != null ? n.value() : -1f;
        if(until > 0 && until <= since){
            throw new IllegalStateException("until must larger than since.");
        }
    }

    @Override
    public boolean isVersionMatched(float expectVersion) {
        if(since < 0){
            return true;
        }
        if(expectVersion >= since){
            if(until >= 0){
                return expectVersion < until;
            }else {
                return true;
            }
        }
        return false;
    }
}
