package com.ds.interceptor;

import com.ds.annotation.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(Authentication.class)){
            Authentication annotation = method.getAnnotation(Authentication.class);
            if(annotation.required()){
                //鉴权：是否具有角色、菜单、按钮权限
            }
        }
        return true;
    }
}
