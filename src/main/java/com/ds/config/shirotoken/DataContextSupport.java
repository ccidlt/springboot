package com.ds.config.shirotoken;

import com.ds.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataContextSupport {
    public static final ThreadLocal<User> dataContext = new ThreadLocal<>();

    private static final User DATA_PERMISSIONS = new User();

    /**
     * 设置当前用户信息
     */
    public static void setDataPermissions(User user) {
        dataContext.set(user);
    }

    /**
     * 获取当前用数据信息
     */
    public static User getDataPermissions() {
        return Optional.ofNullable(dataContext.get()).orElse(DATA_PERMISSIONS);
    }

    /**
     * 移除当前用户数据信息
     */
    public static void close() {
        dataContext.remove();
    }
}
