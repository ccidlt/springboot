package com.ds.service.impl;

import com.ds.dao.RoleDao;
import com.ds.entity.Role;
import com.ds.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public List<Role> findRoleByUserId(Integer id) {
        return roleDao.findRoleByUserId(id);
    }
}
