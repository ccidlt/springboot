package com.ds.config.shirotoken;

import com.ds.entity.Result;
import com.ds.entity.User;
import com.ds.service.UserService;
import com.ds.utils.JWTUtils;
import com.ds.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/shiroToken")
public class ShiroTokenController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User u = userService.findByParam(user);
        if (!Optional.ofNullable(u).isPresent()) {
            return Result.build(201, "用户名或密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", StringUtil.getString(u.getId()));
        String token = JWTUtils.getToken(map, 30 * 60);
        return Result.ok(token);
    }

    /**
     * 获取当前登录用户信息
     *
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
