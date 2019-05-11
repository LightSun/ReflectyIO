package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.MemberProxy;
import com.heaven7.java.reflecty.iota.TypeAdapter;

import java.io.IOException;

/**
 * indicate the member is a commissioner. it can write and read data self. this often extend {@linkplain MemberProxy}.
 * @author heaven7
 * @since 1.0.1
 */
public interface Commissioner{

    /**
     * write the data to the sink as Commissioner like.
     * @param adapter the type adapter of current use
     * @param sink the sink
     * @param value the value to write
     * @throws IOException if I/O error occurs
     */
    void write(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, ReflectyWriter sink, Object value) throws IOException;

    /**
     * read the data self. often you should use {@linkplain MemberProxy#setValue(Object, Object)} to set the read value.
     * @param adapter the type adapter of current
     * @param source the source to read
     * @throws IOException if I/O error occurs
     */
    void read(TypeAdapter<ReflectyWriter, ReflectyReader> adapter, ReflectyReader source) throws IOException;
}
