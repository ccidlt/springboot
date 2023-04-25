package com.ds.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BeanUtil extends BeanUtils {

    public static <T, E> E copyFromFather(T father, E child) throws Exception {

        //判断是否是父子类关系
        if (child.getClass().getSuperclass() != father.getClass()) {
            throw new Exception("参数异常!,非继承关系!");
        }
        Class<?> fatherClass = father.getClass();
        Field[] fields = fatherClass.getFields();
        for (Field field : fields) {
            Method method = fatherClass.getDeclaredMethod("get" + upperFirstChar(field.getName()));
            Object obj = method.invoke(father);
            field.setAccessible(true);
            field.set(child, obj);
        }
        return child;
    }

    private static String upperFirstChar(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //32 是大写小ASCII 字母的差值. 比如 a 是96 A 64
        chars[0] += (1 >> 5);
        return String.valueOf(chars);
    }


    /**
     * 集合数据的拷贝
     *
     * @param sources: 数据源类
     * @param target:  目标类::new(eg: UserVO::new)
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }


    /**
     * 带回调函数的集合数据的拷贝（可自定义字段拷贝规则）
     *
     * @param sources:  数据源类
     * @param target:   目标类::new(eg: UserVO::new)
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

    /**
     * String类型 CanonicalName
     */
    private static final String STRING_TYPE_NAME = "java.lang.String";

    /**
     * 去除实体中属性值空格及换行
     * @param object 实体属性
     */
    public static void trimData(Object object) {
        // 获取实体中所有属性字段
        Field[] fields = ReflectUtil.getFields(object.getClass());
        for (Field field : fields) {
            // 获取属性字段类型
            String canonicalName = field.getType().getCanonicalName();
            // 如果字段是String类型,则去除此字段数据的空格
            if (STRING_TYPE_NAME.equals(canonicalName)) {
                // 获取字段值
                Object fieldValue = ReflectUtil.getFieldValue(object, field);
                if (ObjectUtil.isNotEmpty(fieldValue)){
                    String fieldValueStr = String.valueOf(fieldValue);
                    if (StrUtil.isNotBlank(fieldValueStr)) {
                        // 去掉换行
                        String fieldValueStrNew = fieldValueStr.replaceAll("\r|\n", "");
                        // 将去除前中后的空格后的数据 替换 原数据
                        ReflectUtil.setFieldValue(object, field, fieldValueStrNew.replaceAll(" ", ""));
                    }
                }
            }
        }
    }

}

@FunctionalInterface
interface BeanUtilCallBack<S, T> {

    /**
     * 定义的默认回调方法
     *
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}
