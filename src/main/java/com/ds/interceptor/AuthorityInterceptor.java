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
                //此处是具体查询用户具有哪些权限的
                //是否包含某些角色
                //是否包含这次的请求路径的菜单
                String path = request.getRequestURI();
            }
        }
        /*
        Map<String,Object> rsp = new HashMap<>(2);
        rsp.put("code",403);
        rsp.put("msg","无权访问");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        final PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(rsp));
        return false;
        */
        return true;
    }
}
