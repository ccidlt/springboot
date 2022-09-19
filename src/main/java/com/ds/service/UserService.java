package com.ds.service;

import com.ds.entity.User;

public interface UserService {

    User findByAccount(User user);

    User findByParam(User user);

    User findById(Integer id);

    User getUser(String token);
}
