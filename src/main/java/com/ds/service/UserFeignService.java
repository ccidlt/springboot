package com.ds.service;

import com.ds.entity.Boy;
import com.ds.service.impl.UserFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@FeignClient(name="demo", url = "http://127.0.0.1:${server.port}", /*configuration = FeignConfig.class,*/ fallback = UserFeignServiceImpl.class)
@Service
public interface UserFeignService {

    @RequestMapping(value = "/getBoys",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8",
            headers = {"Content-Type=application/json;charset=UTF-8"})
    List<Boy> getBoys();

}
