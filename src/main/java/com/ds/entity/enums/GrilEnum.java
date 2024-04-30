package com.ds.entity.enums;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @Description: boy对象枚举
 * @Author lt
 * @Date 2023/10/7 15:37
 */
//@JsonFormat(shape= JsonFormat.Shape.OBJECT)
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

    @EnumValue
    private Integer id;
    @JsonValue
    private String value;

    GrilEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    // 若不配置@JsonCreator，jackson反序列化时则使用@JsonValue标记的字段做映射
    @JsonCreator
    public static GrilEnum jacksonInstance(final JsonNode jsonNode) {
        Integer id = jsonNode.asInt();
        GrilEnum[] values = GrilEnum.values();
        for (GrilEnum grilEnum : values) {
            if (grilEnum.getId().equals(id)) {
                return grilEnum;
            }
        }
        return null;
    }

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
