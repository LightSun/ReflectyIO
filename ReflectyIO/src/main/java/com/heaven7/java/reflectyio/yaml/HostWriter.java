package com.heaven7.java.reflectyio.yaml;

public interface HostWriter {

    String nextSpace();

    String currentSpace();

    String previousSpace();

    HostWriter append(String str);
}
