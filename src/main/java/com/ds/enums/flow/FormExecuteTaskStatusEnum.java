package com.ds.enums.flow;

import cn.hutool.core.util.ObjectUtil;
import lombok.ToString;

/**
 * @Description: 单子任务执行状态枚举
 * @Author lt
 * @Date 2023/6/7 13:32
 */
@ToString
public enum FormExecuteTaskStatusEnum {

    UNEXECUTED(0, "未执行"),
    UNDEREXECUTION(1, "执行中"),
    EXECUTED(2, "已执行"),
    ;
    private Integer value;
    private String name;

    FormExecuteTaskStatusEnum(Integer value, String name) {
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
        for (FormExecuteTaskStatusEnum formStatusEnum : FormExecuteTaskStatusEnum.values()) {
            if(ObjectUtil.equals(formStatusEnum.getValue(), value)){
                return formStatusEnum.getName();
            }
        }
        return null;
    }

}
