package com.ds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(getLoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/toLogin",
                "/login",
                "/error",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html/**"
        );
    }

        /**
         * 配置静态资源
         * @param registry
         */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mytest/**").addResourceLocations("classpath:/mytest/");
        registry.addResourceHandler("/fileData/**").addResourceLocations("file:D:/myFile/");
    }

    /**
     * 配置请求视图映射
     * @return
     */
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        //thymeleaf默认提供的视图解析器，映射到template目录下，如果要访问页面自定义的目录，就需要在application.properties中配置
        //1.thymeleaf依赖包
        //2.application.properties中配置
        //  spring.thymeleaf.prefix=classpath:/page/
        //  spring.thymeleaf.suffix=.html

        //thymeleaf优先级大于mvc
        //1.application.properties中配置
        //  spring.mvc.view.prefix=/html/
        //  spring.mvc.view.suffix=.jsp
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        //请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("/html/");
        //请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }
    /**
     * 配置试图
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(resourceViewResolver());
    }

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("POST","GET")
                .allowedOrigins("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter  = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(smt);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        converters.add(mappingJackson2HttpMessageConverter);
    }
}
