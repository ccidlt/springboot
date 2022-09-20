package com.ds.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanUtil extends BeanUtils {

    public static <T,E> E  copyFromFather(T father, E child) throws Exception {

        //判断是否是父子类关系
        if(child.getClass().getSuperclass() != father.getClass()){
            throw  new Exception("参数异常!,非继承关系!");
        }
        Class<?> fatherClass = father.getClass();
        Field[] fields = fatherClass.getFields();
        for (Field field : fields) {
            Method method = fatherClass.getDeclaredMethod("get"+upperFirstChar(field.getName()));
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child,obj);
        }
        return child;
    }

    private static String upperFirstChar(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //32 是大写小ASCII 字母的差值. 比如 a 是96 A 64
        chars[0] +=(1>>5);
        return String.valueOf(chars);
    }


    /**
     * 集合数据的拷贝
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     * @param sources: 数据源类
     * @param target: 目标类::new(eg: UserVO::new)
     * @param callBack: 回调函数
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeanUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }
}

@FunctionalInterface
interface BeanUtilCallBack<S, T> {

    /**
     * 定义的默认回调方法
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
