package com.ds.dao;

import com.ds.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User findByAccount(User user);

    User findByParam(User user);

    User findById(@Param("id") Integer id);
}
