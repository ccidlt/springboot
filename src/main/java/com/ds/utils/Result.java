package com.ds.utils;

public class Result<T> {

    private String code;

    private String msg;

    private T t;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.t = t;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public static Result getResult(String code, String msg){
        return new Result(code, msg);
    }

    public static<T> Result getResult(String code, String msg, T t){
        return new Result(code, msg, t);
    }
}
