package com.ds.config.filter;

import javax.servlet.FilterConfig;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "myFilter", urlPatterns={"/*"})
//@Component
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");//允许跨域访问的域，可以是通配符”*”
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET");
		httpServletResponse.setHeader("Access-Control-Max-Age", "1800");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With, Access-Token, token");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
