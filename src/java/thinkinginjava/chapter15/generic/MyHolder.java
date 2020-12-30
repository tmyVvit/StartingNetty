package thinkinginjava.chapter15.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyHolder<T extends List> {
    private final T obj;
    public MyHolder(T obj) {
        this.obj = obj;
    }

    public T get() {
        return obj;
    }

    public static <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    private void test() {
    }
}
