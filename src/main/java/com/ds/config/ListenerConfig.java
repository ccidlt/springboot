package com.ds.config;

import com.ds.listener.MyHttpSessionListener;
import com.ds.listener.MyServletContextListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean servletContextListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(myServletContextListener());
        return bean;
    }

    @Bean
    public MyServletContextListener myServletContextListener() {
        return new MyServletContextListener();
    }

    @Bean
    public ServletListenerRegistrationBean httpSessionListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(myHttpSessionListener());
        return bean;
    }

    @Bean
    public MyHttpSessionListener myHttpSessionListener() {
        return new MyHttpSessionListener();
    }
}
