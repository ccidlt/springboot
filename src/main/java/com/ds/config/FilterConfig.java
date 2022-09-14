package com.ds.config;

import com.ds.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>();
        bean.setFilter(loginFilter());
        bean.addUrlPatterns("/*");
        bean.setName("myFilter");
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public MyFilter loginFilter() {
        return new MyFilter();
    }
}
