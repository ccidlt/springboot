package com.ds.config.webmvc;

import com.ds.annotation.PassToken;
import com.ds.annotation.UseLoginToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		/*
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, Access-Token, token");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken annotation = method.getAnnotation(PassToken.class);
            if (annotation.required()) {
                return true;
            }
        }
        boolean useLoginToken = true;
        if (method.isAnnotationPresent(UseLoginToken.class)) {
            UseLoginToken annotation = method.getAnnotation(UseLoginToken.class);
            useLoginToken = annotation.required();
        }
        if (!useLoginToken) {
            return true;
        }
		/*String token = request.getHeader("token");
		if(StringUtil.isEmpty(token)){
			token = request.getParameter("token");
		}
		if(StringUtil.isNotEmpty(token)){
			String redisToken = StringUtil.getString(redisTemplate.opsForValue().get(token));
			if(StringUtil.isNotEmpty(redisToken)){
				if(redisTemplate.getExpire(token,TimeUnit.MINUTES) < 10){
					redisTemplate.expire(token, 30, TimeUnit.MINUTES);
				}
				return true;
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		ServletOutputStream out = response.getOutputStream();
		JSONObject result = new JSONObject();
		result.put("code", "401");
		result.put("msg", "请重新登录！");
		out.write(result.toString().getBytes());
		return false;*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
