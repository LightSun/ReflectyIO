package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

/**
 * the plugin which used to create {@linkplain ITypeAdapterManager}
 * @author heaven7
 * @since 1.0.6
 */
public interface ReflectyPlugin {

    /**
     * called on create type adapter manager
     * @return type adapter manager
     */
    ITypeAdapterManager<ReflectyWriter, ReflectyReader> createTypeAdapterManager();

}
