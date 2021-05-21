package com.ds.controller;

import com.ds.service.AspectService;
import com.ds.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class TestCtroller {

    @Resource
    TestService testService;

    @RequestMapping(value="/async",method = RequestMethod.GET)
    public String asyncTask(){
        testService.asyncTask();
        return "async";
    }

    @AspectService(operationType="test",operationName="for test")
    @RequestMapping(value="/aspect",method = RequestMethod.GET)
    public String aspectTask(){
        return "aspect";
    }
}
