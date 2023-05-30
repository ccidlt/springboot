package com.ds.entity;

/**
 * @Description: 返回前端的异常结果
 * @Author lt
 * @Date 2023/5/22 14:27
 */
public class BusinessException extends RuntimeException{
    private Integer code;

    /**
     * 用户展示看到的msg
     */
    private String msg = "";

    /**
     * 系统错误msg，按需展示给前台页面
     * 建议：dubbo返回使用此参数
     */
    private String sysMsg = "";

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Integer code, String msg, String sysMsg) {
        this.code = code;
        this.msg = msg;
        this.sysMsg = sysMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }
}
