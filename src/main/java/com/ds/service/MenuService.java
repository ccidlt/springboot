package com.ds.service;


import com.ds.entity.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> findByRoleId(List<Integer> roleIds);

}
