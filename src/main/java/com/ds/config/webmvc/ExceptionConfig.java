package com.ds.config.webmvc;

import com.ds.entity.BusinessException;
import com.ds.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
@Order(999999)
public class ExceptionConfig {

    private Logger logger = LoggerFactory.getLogger(ExceptionConfig.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        logger.error(message);
        return Result.build(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public Result exceptionHandler(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> conSet = e.getConstraintViolations();
        for (ConstraintViolation<?> con : conSet) {
            String message = con.getMessage();
            sb.append(message).append(";");
        }
        logger.error(sb.toString());
        return Result.build(HttpStatus.BAD_REQUEST.value(), sb.toString());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result exceptionHandler(MissingServletRequestParameterException e) {
        String message = "缺少参数："+e.getParameterName();
        logger.error(message);
        return Result.build(HttpStatus.BAD_REQUEST.value(),message);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public Result handleMissingPathVariableException(MissingPathVariableException e) {
        String message = "缺少参数：" + e.getVariableName();
        logger.error(message);
        return Result.build(HttpStatus.BAD_REQUEST.value(),message);
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        logger.error("系统异常", e);
        return Result.build(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result exceptionHandler(BusinessException e) {
        return Result.build(e.getCode(), e.getMessage());
    }

}
