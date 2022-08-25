package com.ds.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    OK (200,"成功"),
    FAIL(201, "失败"),
    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未经授权"),
    FORBIDDEN(403, "禁止"),
    NOT_FOUND(404, "没有找到"),
    INTERNAL_SERVER_ERROR(500, "内部服务器错误"),
    BAD_GATEWAY(502, "错误的网关"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
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
