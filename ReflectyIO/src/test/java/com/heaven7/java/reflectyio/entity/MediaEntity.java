package com.heaven7.java.reflectyio.entity;

import com.heaven7.java.reflectyio.anno.ReflectyMethodType;

import java.util.Objects;

import static com.heaven7.java.reflectyio.anno.ReflectyMethodType.TYPE_GET;

public final class MediaEntity {

    private transient long startTime;
    private transient long endTime;

    @ReflectyMethodType(TYPE_GET)
    public long[] getTimes(){
        return new long[]{startTime, endTime};
    }

    @ReflectyMethodType
    public void setTimes(long[] times){
        this.startTime = times[0];
        this.endTime = times[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaEntity that = (MediaEntity) o;
        return startTime == that.startTime &&
                endTime == that.endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
