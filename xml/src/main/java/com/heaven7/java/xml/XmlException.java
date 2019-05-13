package com.heaven7.java.xml;

/**
 * @author heaven7
 */
public class XmlException extends RuntimeException{

    public XmlException() {
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlException(Throwable cause) {
        super(cause);
    }
}
