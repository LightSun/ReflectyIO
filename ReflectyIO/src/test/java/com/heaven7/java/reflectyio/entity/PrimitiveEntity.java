package com.heaven7.java.reflectyio.entity;

import java.util.Objects;

public class PrimitiveEntity {

    private byte arg1;
    private short arg2;
    private int arg3;
    private long arg4;
    private float arg5;
    private double arg6;
    private boolean arg7;
    private char arg8;

    public static PrimitiveEntity create(){
        PrimitiveEntity entity = new PrimitiveEntity();
        entity.setArg1((byte) 1);
        entity.setArg2((short) 2);
        entity.setArg3(3);
        entity.setArg4(4);
        entity.setArg5(5.5f);
        entity.setArg6(6.2);
        entity.setArg7(true);
        entity.setArg8((char) 97);
        return entity;
    }

    public byte getArg1() {
        return arg1;
    }

    public void setArg1(byte arg1) {
        this.arg1 = arg1;
    }

    public short getArg2() {
        return arg2;
    }

    public void setArg2(short arg2) {
        this.arg2 = arg2;
    }

    public int getArg3() {
        return arg3;
    }

    public void setArg3(int arg3) {
        this.arg3 = arg3;
    }

    public long getArg4() {
        return arg4;
    }

    public void setArg4(long arg4) {
        this.arg4 = arg4;
    }

    public float getArg5() {
        return arg5;
    }

    public void setArg5(float arg5) {
        this.arg5 = arg5;
    }

    public double getArg6() {
        return arg6;
    }

    public void setArg6(double arg6) {
        this.arg6 = arg6;
    }

    public boolean isArg7() {
        return arg7;
    }

    public void setArg7(boolean arg7) {
        this.arg7 = arg7;
    }

    public char getArg8() {
        return arg8;
    }

    public void setArg8(char arg8) {
        this.arg8 = arg8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveEntity that = (PrimitiveEntity) o;
        return arg1 == that.arg1 &&
                arg2 == that.arg2 &&
                arg3 == that.arg3 &&
                arg4 == that.arg4 &&
                Float.compare(that.arg5, arg5) == 0 &&
                Double.compare(that.arg6, arg6) == 0 &&
                arg7 == that.arg7 &&
                arg8 == that.arg8;
    }

    @Override
    public int hashCode() {

        return Objects.hash(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }
}
