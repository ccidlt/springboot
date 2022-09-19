package com.ds.config.shiro;

import com.ds.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @PostMapping("/login")
    public ServerResponseVO login(@RequestParam(value = "account") String account,
                                  @RequestParam(value = "password") String password) {
        Subject userSubject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            // 登录验证
            userSubject.login(token);
            return ServerResponseVO.success();
        } catch (UnknownAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_NOT_EXIST);
        } catch (DisabledAccountException e) {
            return ServerResponseVO.error(ServerResponseEnum.ACCOUNT_IS_DISABLED);
        } catch (IncorrectCredentialsException e) {
            return ServerResponseVO.error(ServerResponseEnum.INCORRECT_CREDENTIALS);
        } catch (Throwable e) {
            e.printStackTrace();
            return ServerResponseVO.error(ServerResponseEnum.ERROR);
        }
    }

    @GetMapping("/login")
    public ServerResponseVO login() {
        return ServerResponseVO.error(ServerResponseEnum.NOT_LOGIN_IN);
    }

    @GetMapping("/getUser")
    public ServerResponseVO getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return ServerResponseVO.success(user);
    }

    @GetMapping("/logout")
    public ServerResponseVO logout() {
        return ServerResponseVO.success();
    }

    @GetMapping("/auth")
    public String auth() {
        return"已成功登录";
    }

    @GetMapping("/role")
    @RequiresRoles("vip")
    public String role() {
        return"测试Vip角色";
    }

    @GetMapping("/permission")
    @RequiresPermissions(value = {"add", "update"}, logical = Logical.AND)
    public String permission() {
        return"测试Add和Update权限";
    }

}
