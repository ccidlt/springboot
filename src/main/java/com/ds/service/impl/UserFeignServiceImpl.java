package com.ds.service.impl;

import com.ds.entity.Boy;
import com.ds.service.UserFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFeignServiceImpl implements UserFeignService {
    @Override
    public List<Boy> getBoys() {
        return null;
    }
}
