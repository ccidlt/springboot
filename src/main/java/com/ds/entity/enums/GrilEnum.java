package com.ds.entity.enums;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Description: boy对象枚举
 * @Author lt
 * @Date 2023/10/7 15:37
 */
public enum GrilEnum {

    A(1,"黄蓉"),
    B(2,"穆念慈"),
    C(3,"小龙女"),
    D(4,"郭襄"),
    E(5,"赵敏"),
    F(6,"小昭"),
    G(7,"周芷若"),
    H(8,"任盈盈"),
    I(9,"袁紫衣"),

    ;

    GrilEnum(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    @EnumValue
    private Integer id;
    @JsonValue
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static GrilEnum getGrilEnumById(Integer id){
        for (GrilEnum grilEnum : GrilEnum.values()) {
            if(ObjectUtil.equal(grilEnum.getId(), id)){
                return grilEnum;
            }
        }
        return null;
    }
}
