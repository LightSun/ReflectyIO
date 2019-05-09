package com.heaven7.java.reflectyio.temp;

import java.nio.ByteBuffer;

public class KCPTest {

    ///https://github.com/LightSun/kcp
    public static void main(String[] args) {
        KCP kcp = new KCP(10) {
            @Override
            protected void output(byte[] bytes, int size) {
                ByteBuffer buff = ByteBuffer.allocate(size);
                buff.put(bytes, 0, size);
                buff.rewind();
                //session.write(buff);
            }
        };

       // kcp.update_ack();
        //kcp.Input()
    }
}
