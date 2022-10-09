package com.ds.controller;

import com.ds.entity.Result;
import com.ds.entity.User;
import com.ds.service.UserService;
import com.ds.utils.JWTUtils;
import com.ds.utils.RedisUtils;
import com.ds.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("")
public class LoginController {

    @Resource
    private UserService userService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        User u = userService.findByParam(user);
        if(u == null){
            return Result.fail();
        }
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", String.valueOf(u.getId()));
        userMap.put("now", String.valueOf(System.currentTimeMillis()));
        String token = JWTUtils.getToken(userMap);
        redisUtils.set(token, u, 30L, TimeUnit.MINUTES);
        return Result.ok(token);
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtil.isNotEmpty(token)){
            if(redisUtils.exists(token)){
                redisUtils.remove(token);
            }
        }
        return Result.ok();
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @RequestMapping("/getUser")
    public Result getUser(HttpServletRequest request){
        String token = request.getHeader("token");
        return Result.ok(userService.getUser(token));
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @RequestMapping("/editPassword")
    public Result editPassword(@RequestBody User user){
        //修改密码
        //......
        Map<String, String> userMap = new HashMap<>();
        userMap.put("userId", String.valueOf(user.getId()));
        userMap.put("now", String.valueOf(System.currentTimeMillis()));
        String token = JWTUtils.getToken(userMap);
        redisUtils.set(token, user, 30L, TimeUnit.MINUTES);
        return Result.ok(token);
    }
}
