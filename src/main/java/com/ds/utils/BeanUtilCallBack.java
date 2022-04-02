package com.ds.utils;

@FunctionalInterface
public interface BeanUtilCallBack<S, T> {

    /**
     * 定义默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
