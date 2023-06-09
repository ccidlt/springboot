package com.ds.controller.flow;

import com.ds.entity.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 异常同意处理类
 * @Author lt
 * @Date 2023/6/7 11:26
 */
public class FlowExceptionController {

    @ExceptionHandler(FlowException.class)
    @ResponseBody
     public Result flowException(FlowException ex){
         return Result.build(ex.getCode(), ex.getMessage());
     }

}
