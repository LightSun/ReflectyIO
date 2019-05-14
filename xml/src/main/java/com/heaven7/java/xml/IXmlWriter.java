package com.heaven7.java.xml;

import java.io.IOException;

public interface IXmlWriter {

    void bodyText(Object value) throws IOException;

    void element(String name) throws IOException;

    void pop() throws IOException;
}
