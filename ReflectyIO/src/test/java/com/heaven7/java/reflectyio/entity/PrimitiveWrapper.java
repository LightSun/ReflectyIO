package com.heaven7.java.reflectyio.entity;

import java.util.Objects;

public class PrimitiveWrapper {

    private Byte arg1;
    private Short arg2;
    private Integer arg3;
    private Long arg4;
    private Float arg5;
    private Double arg6;
    private Boolean arg7;
    private Character arg8;

    public static PrimitiveWrapper create(){
        PrimitiveWrapper entity = new PrimitiveWrapper();
        entity.setArg1((byte) 1);
        entity.setArg2((short) 2);
        entity.setArg3(3);
        entity.setArg4(4L);
        entity.setArg5(5.5f);
        entity.setArg6(6.2);
        entity.setArg7(true);
        entity.setArg8((char) 97);
        return entity;
    }

    public Byte getArg1() {
        return arg1;
    }

    public void setArg1(Byte arg1) {
        this.arg1 = arg1;
    }

    public Short getArg2() {
        return arg2;
    }

    public void setArg2(Short arg2) {
        this.arg2 = arg2;
    }

    public Integer getArg3() {
        return arg3;
    }

    public void setArg3(Integer arg3) {
        this.arg3 = arg3;
    }

    public Long getArg4() {
        return arg4;
    }

    public void setArg4(Long arg4) {
        this.arg4 = arg4;
    }

    public Float getArg5() {
        return arg5;
    }

    public void setArg5(Float arg5) {
        this.arg5 = arg5;
    }

    public Double getArg6() {
        return arg6;
    }

    public void setArg6(Double arg6) {
        this.arg6 = arg6;
    }

    public Boolean getArg7() {
        return arg7;
    }

    public void setArg7(Boolean arg7) {
        this.arg7 = arg7;
    }

    public Character getArg8() {
        return arg8;
    }

    public void setArg8(Character arg8) {
        this.arg8 = arg8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrimitiveWrapper that = (PrimitiveWrapper) o;
        return Objects.equals(arg1, that.arg1) &&
                Objects.equals(arg2, that.arg2) &&
                Objects.equals(arg3, that.arg3) &&
                Objects.equals(arg4, that.arg4) &&
                Objects.equals(arg5, that.arg5) &&
                Objects.equals(arg6, that.arg6) &&
                Objects.equals(arg7, that.arg7) &&
                Objects.equals(arg8, that.arg8);
    }

    @Override
    public int hashCode() {

        return Objects.hash(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }
}
