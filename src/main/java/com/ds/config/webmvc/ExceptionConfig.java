package com.ds.config.webmvc;

import com.ds.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionConfig {

    private Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

    /**
     * 捕捉其他所有异常
     * */
    @ExceptionHandler(Exception.class)
    public Result globalException(HttpServletRequest request, Exception e) {
        if (e instanceof NoHandlerFoundException) {
            return Result.build(HttpStatus.NOT_FOUND.value(), "找不到资源");
        }else if (e instanceof TypeMismatchException) {
            TypeMismatchException te = (TypeMismatchException)e;
            if (Double.class.equals(te.getRequiredType()) ||  Integer.class.equals(te.getRequiredType())) {
                String res = String.format("请求错误, %s不是一个数字", te.getValue());
                return Result.build(HttpStatus.BAD_REQUEST.value(), res);
            } else {
                String res = String.format("请求错误, %s无效, 需要%s类型", te.getValue(), te.getRequiredType().getName());
                return Result.build(HttpStatus.BAD_REQUEST.value(), res);
            }
        }else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException me = (MissingServletRequestParameterException)e;
            String res = String.format("请求错误, 参数：%s是必须的, 类型：%s", me.getParameterName(), me.getParameterType());
            return Result.build(HttpStatus.BAD_REQUEST.value(), res);
        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException me = (MethodArgumentNotValidException)e;
            return Result.build(HttpStatus.BAD_REQUEST.value(), me.getBindingResult().getFieldError().getDefaultMessage());
        }else if(e instanceof BindException){
            BindException me = (BindException)e;
            return Result.build(HttpStatus.BAD_REQUEST.value(), me.getBindingResult().getFieldError().getDefaultMessage());
        }else{
            return Result.build(HttpStatus.SERVICE_UNAVAILABLE.value(), this.getClass().getSimpleName()+": "+e.getMessage());
        }
    }

}
