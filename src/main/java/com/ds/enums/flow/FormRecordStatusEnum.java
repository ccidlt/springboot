package com.ds.enums.flow;

import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;

/**
 * @Description: 单子记录状态枚举
 * @Author lt
 * @Date 2023/6/7 13:32
 */
@ToString
public enum FormRecordStatusEnum {

    DRAFT(1, "草稿"),
    ESTABLISH(2, "创建"),
    COMPLETED(3, "同意"),
    REJECTED(4, "拒绝"),
    ;
    private Integer value;
    private String name;

    FormRecordStatusEnum(Integer value, String name) {
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
        for (FormRecordStatusEnum formStatusEnum : FormRecordStatusEnum.values()) {
            if(ObjectUtil.equals(formStatusEnum.getValue(), value)){
                return formStatusEnum.getName();
            }
        }
        return null;
    }

}
