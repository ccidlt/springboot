package com.ds.enums.flow;

import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;

/**
 * @Description: 是否删除枚举
 * @Author lt
 * @Date 2023/6/7 13:32
 */
@ToString
public enum IsDeleteEnum {

    NO(0, "否"),
    YRS(1, "是"),
    ;
    private Integer value;
    private String name;

    IsDeleteEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(Integer value){
        for (IsDeleteEnum isDeleteEnum : IsDeleteEnum.values()) {
            if(ObjectUtil.equals(isDeleteEnum.getValue(), value)){
                return isDeleteEnum.getName();
            }
        }
        return null;
    }

}
