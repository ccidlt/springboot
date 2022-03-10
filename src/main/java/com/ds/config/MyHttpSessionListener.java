package com.ds.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * request.getSession()触发sessionCreated
 * request.getSession().invalidate()触发sessionDestroyed
 */
//@WebListener
//@Component
public class MyHttpSessionListener implements HttpSessionListener {
	public static int online = 0;

	/**
	 * 创建session
	 * @param se
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
        online ++;
	}

	/**
	 * 销毁session
	 * @param se
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		online --;
	}

}
