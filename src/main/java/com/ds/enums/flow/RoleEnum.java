package com.ds.enums.flow;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;

/**
 * @Description: 角色编码枚举
 * @Author lt
 * @Date 2023/6/7 13:32
 */
@ToString
public enum RoleEnum {

    STUDENT("STUDENT", "学生"),
    TEACHER("TEACHER", "教师"),
    PRINCIPAL("PRINCIPAL", "校长"),
    ;
    private String code;
    private String name;

    RoleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String code){
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if(StrUtil.equals(roleEnum.getCode(), code)){
                return roleEnum.getName();
            }
        }
        return null;
    }

}
