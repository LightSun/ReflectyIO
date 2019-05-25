package com.heaven7.java.reflectyio.entity;

import java.util.Arrays;

public class PrimitiveArrayEntity {

    private byte[] arg1;
    private short[] arg2;
    private int[] arg3;
    private long[] arg4;
    private float[] arg5;
    private double[] arg6;
    private boolean[] arg7;
    private char[] arg8;

    public static PrimitiveArrayEntity create(){
        PrimitiveArrayEntity entity = new PrimitiveArrayEntity();
        entity.initAll(3);
        entity.arg1[0] = 1;
        entity.arg1[1] = 2;
        entity.arg1[2] = 3;

        entity.arg2[0] = 1;
        entity.arg2[1] = 2;
        entity.arg2[2] = 3;

        entity.arg3[0] = 1;
        entity.arg3[1] = 2;
        entity.arg3[2] = 3;

        entity.arg4[0] = 1;
        entity.arg4[1] = 2;
        entity.arg4[2] = 3;

        entity.arg5[0] = 1;
        entity.arg5[1] = 2;
        entity.arg5[2] = 3;

        entity.arg6[0] = 1;
        entity.arg6[1] = 2;
        entity.arg6[2] = 3;

        entity.arg7[0] = true;
        entity.arg7[1] = false;
        entity.arg7[2] = true;

        entity.arg8[0] = 97;
        entity.arg8[1] = 'h';
        entity.arg8[2] = 99;
        return entity;
    }

    public void initAll(int len){
        arg1 = new byte[len];
        arg2 = new short[len];
        arg3 = new int[len];
        arg4 = new long[len];
        arg5 = new float[len];
        arg6 = new double[len];
        arg7 = new boolean[len];
        arg8 = new char[len];
    }

    public byte[] getArg1() {
        return arg1;
    }
    public void setArg1(byte[] arg1) {
        this.arg1 = arg1;
    }

    public short[] getArg2() {
        return arg2;
    }
    public void setArg2(short[] arg2) {
        this.arg2 = arg2;
    }

    public int[] getArg3() {
        return arg3;
    }
    public void setArg3(int[] arg3) {
        this.arg3 = arg3;
    }

    public long[] getArg4() {
        return arg4;
    }
    public void setArg4(long[] arg4) {
        this.arg4 = arg4;
    }

    public float[] getArg5() {
        return arg5;
    }
    public void setArg5(float[] arg5) {
        this.arg5 = arg5;
    }

    public double[] getArg6() {
        return arg6;
    }
    public void setArg6(double[] arg6) {
        this.arg6 = arg6;
    }

    public boolean[] getArg7() {
        return arg7;
    }
    public void setArg7(boolean[] arg7) {
        this.arg7 = arg7;
    }

    public char[] getArg8() {
        return arg8;
    }
    public void setArg8(char[] arg8) {
        this.arg8 = arg8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveArrayEntity that = (PrimitiveArrayEntity) o;
        return Arrays.equals(arg1, that.arg1) &&
                Arrays.equals(arg2, that.arg2) &&
                Arrays.equals(arg3, that.arg3) &&
                Arrays.equals(arg4, that.arg4) &&
                Arrays.equals(arg5, that.arg5) &&
                Arrays.equals(arg6, that.arg6) &&
                Arrays.equals(arg7, that.arg7) &&
                Arrays.equals(arg8, that.arg8);
    }

    @Override
    public int hashCode() {

        int result = Arrays.hashCode(arg1);
        result = 31 * result + Arrays.hashCode(arg2);
        result = 31 * result + Arrays.hashCode(arg3);
        result = 31 * result + Arrays.hashCode(arg4);
        result = 31 * result + Arrays.hashCode(arg5);
        result = 31 * result + Arrays.hashCode(arg6);
        result = 31 * result + Arrays.hashCode(arg7);
        result = 31 * result + Arrays.hashCode(arg8);
        return result;
    }
}
