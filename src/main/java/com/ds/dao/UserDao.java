package com.ds.dao;

import com.ds.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserDao {

    User findByAccount(User user);

    User findByParam(User user);

    User findById(@Param("id") Integer id);

    String callProcedure1(Integer id);

    Map<String,Object> callProcedure2(Map<String, Object> paramMap);
}
