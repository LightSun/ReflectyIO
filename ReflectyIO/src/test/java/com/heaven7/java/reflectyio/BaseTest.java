package com.heaven7.java.reflectyio;

import com.heaven7.java.reflecty.TypeToken;
import com.heaven7.java.reflectyio.entity.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class BaseTest {

    final StringWriter mWriter = new StringWriter();

    @Test
    public void testPrimitive()  throws IOException{
        PrimitiveEntity entity = PrimitiveEntity.create();
        TypeToken<PrimitiveEntity> token = TypeToken.get(PrimitiveEntity.class);

        testJson(token, entity);
        testXml(token, entity);
        testYaml(token, entity);
    }

    @Test
    public void testPrimitiveArray()  throws IOException{
        PrimitiveArrayEntity entity = PrimitiveArrayEntity.create();
        TypeToken<PrimitiveArrayEntity> token = TypeToken.get(PrimitiveArrayEntity.class);

        testJson(token, entity);
        testXml(token, entity);
        testYaml(token, entity);
    }
    @Test
    public void testPrimitiveWrapper()  throws IOException{
        PrimitiveWrapper entity = PrimitiveWrapper.create();
        TypeToken<PrimitiveWrapper> token = TypeToken.get(PrimitiveWrapper.class);

        testJson(token, entity);
        testXml(token, entity);
        testYaml(token, entity);
    }

    @Test
    public void testSimple() throws IOException{
        Info info = new Info();
        info.setAddr("addr1");
        info.setPhone("12345");
        TypeToken<Info> token = TypeToken.get(Info.class);

        testJson(token, info);
        testXml(token, info);
        testYaml(token, info);
    }

    @Test
    public void testMethod() throws IOException {
        MediaEntity me = new MediaEntity();
        me.setTimes(new long[]{5, 199});

        TypeToken<MediaEntity> token = TypeToken.get(MediaEntity.class);

        testJson(token, me);
        testXml(token, me);
        testYaml(token, me);
    }

    protected void clean(){
        StringBuffer buffer = mWriter.getBuffer();
        buffer.delete(0, buffer.length());
    }

    protected void testXml(TypeToken<?> tt, Object raw) throws IOException {
        ReflectyIo reflectyIo = new ReflectyIo()
                .xml()
                .typeToken(tt)
                .build();

        reflectyIo.write(mWriter, raw);
        //System.out.println(mWriter.toString());
        Object obj = reflectyIo.read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    protected void testJson(TypeToken<?> tt, Object raw) throws IOException {
        ReflectyIo reflectyIo = new ReflectyIo()
                .json()
                .typeToken(tt)
                .build();

        reflectyIo.write(mWriter, raw);
        System.out.println(mWriter.toString());
        Object obj = reflectyIo.read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    protected void testYaml(TypeToken<?> tt, Object raw) throws IOException {
        ReflectyIo reflectyIo = new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build();

        reflectyIo.write(mWriter, raw);
        //System.out.println(mWriter.toString());
        Object obj = reflectyIo.read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
}
