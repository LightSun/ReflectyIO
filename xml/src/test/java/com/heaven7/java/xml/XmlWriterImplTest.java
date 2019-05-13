package com.heaven7.java.xml;

import org.junit.Test;

import java.io.StringWriter;

public class XmlWriterImplTest {

    @Test
    public void test1() throws Exception{
        StringWriter writer = new StringWriter();
        XmlWriterImpl xml = new XmlWriterImpl(writer);
        xml.element("meow")
                .attribute("moo", "cow")
                .element("child")
                    .attribute("moo", "cow")
                    .element("child")
                        .attribute("moo", "cow")
                        .text("XML is like violence. If it doesn't solve your problem, you're not using enough of it.")
                    .pop()
                .pop()
            .pop();
        System.out.println(writer);

        XmlReaderImpl.Element element = new XmlReaderImpl().parse(writer.toString());
        System.out.println(element);
    }

}
