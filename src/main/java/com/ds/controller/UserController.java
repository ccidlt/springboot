package com.ds.controller;

import com.ds.entity.Boy;
import com.ds.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@Api("测试接口")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping(value="/getBoys",method = RequestMethod.POST)
    @ApiOperation("Mybatis测试")
    public List<Boy> getBoys(){
        return userService.getBoys();
    }

    @RequestMapping(value="/getBoysById",method = RequestMethod.GET)
    @ApiOperation("MybatisPlus测试1")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="主键", required = true, paramType = "query", dataType = "Integer")
    })
    public List<Boy> getBoysByParam(@RequestParam("id") Integer id){
        Boy boy = new Boy();
        boy.setName("杨过");
        return userService.queryBoy(boy);
    }

    @RequestMapping(value="/getBoysByParam",method = RequestMethod.GET)
    @ApiOperation("MybatisPlus测试2")
    public List<Boy> getBoysByParam(){
        Boy boy = new Boy();
        boy.setName("杨过");
        return userService.queryBoy(boy);
    }

}
