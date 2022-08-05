package com.ds.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private String code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result() {
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
        return new Result<T>(code, msg, data);
    }

    public static Result success(){
        return new Result("200", "操作成功");
    }

    public static<T> Result success(T data){
        return new Result<T>("200", "操作成功",data);
    }

    public static<T> Result success(String msg, T data){
        return new Result<T>("200", msg, data);
    }

    public static Result error(){
        return new Result("500", "操作失败");
    }

    public static<T> Result error(T data){
        return new Result<T>("500", "操作失败", data);
    }

    public static Result error(String msg){
        return new Result("500", msg);
    }

    public static<T> Result error(String msg, T data){
        return new Result<T>("500", msg, data);
    }

}
