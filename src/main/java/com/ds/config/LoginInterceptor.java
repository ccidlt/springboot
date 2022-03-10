package com.ds.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		String token = request.getHeader("token");

		/*if(StringUtils.isEmpty(token)){
			token = request.getParameter("token");
		}
		String redisToken = (String)redisTemplate.opsForValue().get("token");
		if(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(redisToken)){
			redisTemplate.expire(token,30, TimeUnit.MINUTES);
			return true;
		}
		response.sendRedirect("/");
		return false;*/

		/*response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		//PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		JSONObject result = new JSONObject();
		if(!"lt".equals(token)){
			result.put("suc", false);
			result.put("msg", "无法访问");
			//out.print(result.toString());
			out.write(result.toString().getBytes());
			return false;
		}*/

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
