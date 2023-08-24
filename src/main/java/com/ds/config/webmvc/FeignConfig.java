package com.ds.config.webmvc;

import cn.hutool.core.util.StrUtil;
import com.ds.utils.StringUtil;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StringUtil.isNotEmpty(token)) {
            requestTemplate.header("token", token);
            requestTemplate.query("token", token);
        }
        requestTemplate.header("ip", request.getRemoteAddr());

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                if(!StrUtil.equals(name,"token"))requestTemplate.header(name, values);
            }
        }
    }
}
