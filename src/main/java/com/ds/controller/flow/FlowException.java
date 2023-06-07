package com.ds.controller.flow;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 11:28
 */
public class FlowException extends RuntimeException {

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
