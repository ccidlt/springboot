package com.ds.config.authority;

/**
 * 数据范围权限值相关枚举，模板
 */
public enum DataScopeViewTypeEnum {
    // 不能查看= 0
    //无权限
    VIEW_NONE(0, "无权限"),
    // 查看所有的= 1
    VIEW_ALL(1, "查看所有的"),

    // 查看自己的= 2
    VIEW_ME(2, "查看自己的"),

    // 查看本单位的= 3
    VIEW_COMPANY(3, "查看本单位的"),

    // 查看本单位及其下级单位的= 4
    VIEW_COMPANY_AND_SUB(4, "查看本单位及其下级单位的"),

    ;

    DataScopeViewTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DataScopeViewTypeEnum getByValue(Integer value) {
        for (DataScopeViewTypeEnum transactType : values()) {
            if (transactType.getCode().equals(value)) {
                return transactType;
            }
        }
        return null;
    }

    public static String getValues(String code) {
        for (DataScopeViewTypeEnum enums : values()) {
            if(enums.getCode().equals(code)){
                return enums.getName();
            }
        }
        return null;
    }
}
