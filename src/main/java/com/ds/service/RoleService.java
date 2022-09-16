package com.ds.service;

import com.ds.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findRoleByUserId(Integer id);

}
