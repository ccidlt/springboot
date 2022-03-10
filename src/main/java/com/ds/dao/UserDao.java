package com.ds.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ds.entity.Boy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper<Boy> {

    public List<Boy> getBoys();
}
