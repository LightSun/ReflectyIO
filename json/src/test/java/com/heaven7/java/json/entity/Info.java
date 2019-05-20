package com.heaven7.java.json.entity;

import java.util.Objects;

public class Info {

    private String addr;
    private String phone;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return Objects.equals(addr, info.addr) &&
                Objects.equals(phone, info.phone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(addr, phone);
    }
}
