package com.ds.config;

import com.ds.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public Result globalException(HttpServletRequest request, Throwable ex) {
        if (ex instanceof NoHandlerFoundException) {
            return new Result("404" , "未找到页面: 请联系管理员");
        }
        else{
            logger.error("系统异常： "+ex.getMessage());
            return new Result("500" , "访问出错,请联系管理员");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
