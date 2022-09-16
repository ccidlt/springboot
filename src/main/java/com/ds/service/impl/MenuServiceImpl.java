package com.ds.service.impl;

import com.ds.dao.MenuDao;
import com.ds.entity.Menu;
import com.ds.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Override
    public List<Menu> findByRoleId(List<Integer> roleIds) {
        return menuDao.findByRoleId(roleIds);
    }

}
