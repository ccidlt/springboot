package com.ds.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ds.entity.Boy;

import java.util.List;
import java.util.Map;

public interface BoyService extends IService<Boy> {

    List<Boy> getBoys();

    List<Boy> queryBoy();

    Boy queryBoy(int id);

    List<Boy> queryBoy(List<Integer> ids);

    List<Boy> queryBoy(Map<String, Object> map);

    List<Boy> queryBoy(Boy boy);

    int addBoy(Boy boy);

    Boy addBoy2(Boy boy);

    int updateBoy(Boy boy);

    int deleteBoy(int id);

    Page<Boy> queryBoy(int pagenum, int pagesize);
}
