package com.heaven7.java.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * just wrap list to impl an array like libgdx's Array.
 * @param <T> the element type
 * @author heaven7
 */
/*public*/ class Array<T> implements Iterable<T>{

    private final List<T> mList;

    public Array(int capacity) {
        mList = new ArrayList<>();
    }

    public Array() {
        this(16);
    }

    public int size(){
        return mList.size();
    }

    @Override
    public Iterator<T> iterator() {
        return mList.iterator();
    }

    public void clear() {
        mList.clear();
    }

    public T peek() {
        int size = mList.size();
        if(size > 0){
            return mList.get(size - 1);
        }
        return null;
    }
    public T pop () {
        int size = mList.size();
        if(size > 0){
            return mList.remove(size - 1);
        }
        throw new IllegalStateException("Array is empty.");
    }

    public T get(int index) {
        return mList.get(index);
    }

    public void add(T element) {
        mList.add(element);
    }

    public T removeIndex(int index) {
        return mList.remove(index);
    }

    public boolean removeValue(final T child, boolean indentity) {
        return mList.removeIf(new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return indentity ? t == child : t.equals(child);
            }
        });
    }
}
