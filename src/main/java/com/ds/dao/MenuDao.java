package com.ds.dao;

import com.ds.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {

    List<Menu> findByRoleId(List<Integer> roleIds);
}
