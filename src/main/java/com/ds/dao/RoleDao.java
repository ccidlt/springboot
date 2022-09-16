package com.ds.dao;

import com.ds.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {

    List<Role> findRoleByUserId(@Param("userId") Integer userId);

}
