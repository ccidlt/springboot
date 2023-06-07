package com.ds.enums.flow;

import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;

/**
 * @Description: 单子状态枚举
 * @Author lt
 * @Date 2023/6/7 13:32
 */
@ToString
public enum FormStatusEnum {

    DRAFT(1, "草稿"),
    INCOMPLETE(2, "未完成"),
    COMPLETED(3, "已完成"),
    REJECTED(4, "被拒绝"),
    CLOSE(5, "关闭"),
    ;
    private Integer value;
    private String name;

    FormStatusEnum(Integer value, String name) {
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
        for (FormStatusEnum formStatusEnum : FormStatusEnum.values()) {
            if(ObjectUtil.equals(formStatusEnum.getValue(), value)){
                return formStatusEnum.getName();
            }
        }
        return null;
    }

}
