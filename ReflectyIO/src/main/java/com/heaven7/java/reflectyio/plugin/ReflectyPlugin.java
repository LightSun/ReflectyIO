package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.Reader;
import java.io.Writer;

/**
 * the plugin which used to create {@linkplain ITypeAdapterManager} , {@linkplain ReflectyWriter} and {@linkplain ReflectyReader}.
 * @author heaven7
 * @since 1.0.6
 */
public interface ReflectyPlugin {

    /**
     * called on create type adapter manager
     * @return type adapter manager
     */
    ITypeAdapterManager<ReflectyWriter, ReflectyReader> createTypeAdapterManager();

    /**
     * create reflecty writer
     * @param writer the writer
     * @return the reflecty writer. never null
     * @since 1.0.7
     */
    ReflectyWriter createReflectyWriter(Writer writer);

    /**
     * create reflecty reader
     * @param reader the base reader
     * @return the reflecty reader, never null.
     * @since 1.0.7
     */
    ReflectyReader createReflectyReader(Reader reader);

}
