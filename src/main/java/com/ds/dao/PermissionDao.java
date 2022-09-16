package com.ds.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {

    List<String> findByRoleId(@Param("roleIds") List<Integer> roleIds);

}
