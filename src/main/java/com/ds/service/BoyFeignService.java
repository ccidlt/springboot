package com.ds.service;

import com.ds.entity.Boy;
import com.ds.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


//@FeignClient(name = "demo", url = "http://127.0.0.1:${server.port}", /*configuration = FeignConfig.class,*/ fallback = BoyFeignServiceImpl.class)
@FeignClient(name = "springboot"/*,configuration = FeignConfig.class, fallback = BoyFeignServiceImpl.class*/)
@Service
public interface BoyFeignService {

    @RequestMapping(value = "/getBoys",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8",
            headers = {"Content-Type=application/json;charset=UTF-8"})
    List<Boy> getBoys();

    @RequestMapping(value = "/globalTransactional", method = RequestMethod.POST)
    Result globalTransactional();

}
