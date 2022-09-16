package com.ds.service;

import java.util.List;

public interface PermissionService {

    List<String> findByRoleId(List<Integer> roleIds);

}
