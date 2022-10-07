package com.ds.service.impl;

import com.ds.dao.UserDao;
import com.ds.entity.User;
import com.ds.service.UserService;
import com.ds.utils.JWTUtils;
import com.ds.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User findByAccount(User user) {
        return userDao.findByAccount(user);
    }

    @Override
    public User findByParam(User user) {
        return userDao.findByParam(user);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public User getUser(String token) {
        if(StringUtil.isEmpty(token)){
            return null;
        }
        if(JWTUtils.verify(token)){
            String userId = JWTUtils.getClaim(token,"userId");
            return findById(Integer.valueOf(userId));
        }
        return null;
    }
}
