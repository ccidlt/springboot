package com.ds.service.impl;

import com.ds.dao.PermissionDao;
import com.ds.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<String> findByRoleId(List<Integer> roleIds) {
        return permissionDao.findByRoleId(roleIds);
    }
}
