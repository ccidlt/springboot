package com.ds.service.impl;

import com.ds.entity.Boy;
import com.ds.entity.Result;
import com.ds.service.BoyFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BoyFeignServiceImpl implements BoyFeignService {
    @Override
    public List<Boy> getBoys() {
        return null;
    }

    @Override
    public Result globalTransactionalTest() {
        return null;
    }
}
