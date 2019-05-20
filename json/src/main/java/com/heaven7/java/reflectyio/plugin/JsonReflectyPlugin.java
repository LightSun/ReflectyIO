package com.heaven7.java.reflectyio.plugin;

import com.heaven7.java.json.JsonReader;
import com.heaven7.java.json.JsonWriter;
import com.heaven7.java.reflecty.iota.ITypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyReader;
import com.heaven7.java.reflectyio.ReflectyTypeAdapterManager;
import com.heaven7.java.reflectyio.ReflectyWriter;

import java.io.Reader;
import java.io.Writer;

/**
 * the json reflecty plugin
 * @author heaven7
 */
public final class JsonReflectyPlugin implements ReflectyPlugin {

    @Override
    public ITypeAdapterManager<ReflectyWriter, ReflectyReader> createTypeAdapterManager() {
        return new ReflectyTypeAdapterManager();
    }

    @Override
    public ReflectyWriter createReflectyWriter(Writer writer) {
        return new JsonWriter(writer);
    }

    @Override
    public ReflectyReader createReflectyReader(Reader reader) {
        return new JsonReader(reader);
    }
}
