package com.ds.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
@Order(4)
public class ClassUtil<T> {

    /**
     * 新增时排重
     *
     * @param <T>
     * @param u
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public boolean addIsHave(T o, IService<T> service) throws IllegalArgumentException, IllegalAccessException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        Boolean result = false;

        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // 获取字段的值
            // System.out.println(field.getName() + ":" + field.get(o) );
            String value = field.getType().toString();
            // System.out.println(value);
            if (!field.getName().equals("serialVersionUID") && field.get(o) != null && !value.equals("boolean")) {
                boolean isTableField = field.isAnnotationPresent(TableField.class);
                if (isTableField) {
                    TableField tableField = field.getAnnotation(TableField.class);

                    String fieldValue = tableField.value();
                    // System.out.println(fieldValue + "&&&&&&" + field.getName() + "&&&&&&&&&" + field.get(o));

                    queryWrapper.eq("" + fieldValue + "", field.get(o));
                }
            }
        }
        List<T> list = service.list(queryWrapper);
        if (list.size() > 0) {
            result = true;
        }
        return result;

    }

    /**
     * 更新时排重
     * @param o
     * @param service
     * @param id
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public boolean updateIsHave(T o, IService<T> service, String id)
            throws IllegalArgumentException, IllegalAccessException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        Boolean result = false;

        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            // 获取字段的值
            // System.out.println(field.getName() + ":" + field.get(o) );
            String value = field.getType().toString();
            // System.out.println(value);
            if (!field.getName().equals("serialVersionUID") && field.get(o) != null && !value.equals("boolean")) {
                boolean isTableField = field.isAnnotationPresent(TableField.class);
                if (isTableField) {
                    TableField tableField = field.getAnnotation(TableField.class);

                    String fieldValue = tableField.value();
                    // System.out.println(fieldValue + "&&&&&&" + field.getName() + "&&&&&&&&&" + field.get(o));
                    queryWrapper.eq("" + fieldValue + "", field.get(o));
                }
            }
        }
        List<T> list = service.list(queryWrapper);
        if (list.size() == 1) {
            T g = list.get(0);
            for (Field field : g.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                boolean isIdField = field.isAnnotationPresent(TableId.class);
                if (isIdField) {
                    // System.out.println(field.get(g));
                    if (!field.get(g).equals(id)) {
                        result = true;
                    }
                }
            }
        } else if (list.size() > 1) {
            result = true;
        }
        return result;
    }

}
