package com.ds.config;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
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
