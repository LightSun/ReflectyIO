package com.heaven7.java.xml;

import java.io.IOException;

public interface Tree {

    boolean hasNextChild();

    Tree nextChild();

    Tree getParent();

    String getBodyText() throws IOException;

    String getName() throws IOException;

    String getValue();

    /** end and return to parent */
   // void end();
}
