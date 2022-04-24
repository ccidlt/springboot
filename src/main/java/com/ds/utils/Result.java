package com.ds.utils;

public class Result<T> {

    //1-成功，0-失败
    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(T data) {
        this.code = "200";
        this.msg = "操作成功";
        this.data = data;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result getResult(String code, String msg){
        return new Result(code, msg);
    }

    public static<T> Result getResult(String code, String msg, T data){
        return new Result(code, msg, data);
    }

    public static<T> Result success(T data){
        return new Result(data);
    }

    public static Result error(){
        return new Result("500", "操作失败");
    }

    public static<T> Result error(T data){
        return new Result("500", "操作失败", data);
    }

}
