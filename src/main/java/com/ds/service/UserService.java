package com.ds.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ds.entity.Boy;

import java.util.List;
import java.util.Map;

public interface UserService {

    public List<Boy> getBoys();

    public List<Boy> queryBoy();

    public Boy queryBoy(int id);

    public List<Boy> queryBoy(List<Integer> ids);

    public List<Boy> queryBoy(Map<String,Object> map);

    public List<Boy> queryBoy(Boy boy);

    public int addBoy(Boy boy);
    public Boy addBoy2(Boy boy);

    public int updateBoy(Boy boy);

    public int deleteBoy(int id);

    public Page<Boy> queryBoy(int pagenum, int pagesize);
}
