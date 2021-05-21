package com.ds.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class MyServletContextListener implements ServletContextListener {
	private static WebApplicationContext context;

	/**
	 * MyServletContextListener初始化
	 * @param sce
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context= WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	}

	/**
	 * MyServletContextListener销毁
	 * @param sce
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	/**
	 * 获得对象
	 * */
	public static WebApplicationContext getInitContext(){
		return context;
	}
}
