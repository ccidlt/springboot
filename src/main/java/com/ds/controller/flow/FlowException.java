package com.ds.controller.flow;

import java.io.Serializable;

/**
 * @Description: 异常类
 * @Author lt
 * @Date 2023/6/7 11:28
 */
public class FlowException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -461532980899578676L;

    private Integer code;

    private String message;

    public FlowException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
