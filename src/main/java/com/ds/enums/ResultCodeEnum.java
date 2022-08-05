package com.ds.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"操作成功"),
    FAIL(201, "操作失败"),
    PARAM_ERROR( 202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    LOGIN_AUTH(205, "未登陆"),
    PERMISSION(206, "没有权限"),
    ;

    private Integer code;
    private String value;

    ResultCodeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValues(Integer code) {
        for (ResultCodeEnum enums : ResultCodeEnum.values()) {
            if(enums.getCode().equals(code)){
                return enums.getValue();
            }
        }
        return null;
    }

}
