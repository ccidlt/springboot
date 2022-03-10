package com.ds.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Resource
	RedisTemplate<String,Object> redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		String token = request.getHeader("token");
//		if(StringUtils.isEmpty(token)){
//			token = request.getParameter("token");
//		}
//		String redisToken = (String)redisTemplate.opsForValue().get("token");
//		if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(redisToken)){
//			redisTemplate.expire(token,30, TimeUnit.MINUTES);
//			return true;
//		}
//		response.sendRedirect("/");
//		return false;
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
