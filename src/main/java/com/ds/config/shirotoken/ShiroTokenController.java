package com.ds.config.shirotoken;

import com.ds.entity.Result;
import com.ds.entity.User;
import com.ds.utils.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/shiroToken")
public class ShiroTokenController {

    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        redisUtils.set(uuid, user, 30L, TimeUnit.MINUTES);//redis三十分钟有效期
        return Result.ok(uuid);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public Result getUserInfo() {
        User user = DataContextSupport.getDataPermissions();
        return Result.ok(user);
    }

    @GetMapping("/test1")
    @RequiresPermissions("sys:user:del")
    public Result test1() {
        User user = DataContextSupport.getDataPermissions();
        return Result.ok(user);
    }

    @GetMapping("/test2")
    @RequiresPermissions("sys:role:delete")
    public Result test2() {
        User user = DataContextSupport.getDataPermissions();
        return Result.ok(user);
    }

}
