package com.ds.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.ds.entity.Boy;
import com.ds.entity.Result;
import com.ds.service.BoyFeignService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BoyFeignServiceImpl implements BoyFeignService {

    @Resource
    private BoyServiceImpl boyService;

    @Override
    public List<Boy> getBoys() {
        return null;
    }

    @Override
    public Result globalTransactional() {
        return null;
    }

    /**
     * 调用主服务支持全局事务提交、回滚
     * @param boy1
     * @param boy2
     * @return
     */
    @DSTransactional
    public Integer dsTransactional(Boy boy1, Boy boy2){
        Integer result1 = boyService.addBoyByDs1(boy1);
        Integer result2 = boyService.addBoyByDs2(boy2);
        return result1+result2;
    }

}
