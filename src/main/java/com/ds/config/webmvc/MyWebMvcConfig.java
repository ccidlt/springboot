package com.ds.config.webmvc;

import com.ds.config.perm.PermissionInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
    @Bean
    public PermissionInterceptor getPermissionInterceptor(){
        return new PermissionInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/toLogin",
                    "/login",
                    "/error",
                    "/**/*.html",
                    "/**/*.js",
                    "/**/*.css",
                    "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html/**",
                    "/shiro/login", "/shiroToken/login"
                ).order(1);
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/toLogin",
                    "/login",
                    "/error",
                    "/**/*.html",
                    "/**/*.js",
                    "/**/*.css",
                    "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html/**",
                    "/shiro/login", "/shiroToken/login"
                ).order(2);
    }

    /**
     * 配置静态资源
     * @param registry
     */
    @Value("${filePath}")
    private String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mytest/**").addResourceLocations("classpath:/mytest/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+filePath+"/");
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
                .allowedMethods("POST","GET","PUT","DELETE","HEAD","OPTIONS")
                .allowedOrigins("*");
    }

    /**
     *后端返回给前端时间格式字符串，也可以用@JsonFormat
     * @param converters
     */
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

    /**
     * 前端给后端时间字符串转换成Date，也可以用@DateTimeFormat
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                List<String> formarts = new ArrayList<String>(4){{
                    add("yyyy-MM");
                    add("yyyy-MM-dd");
                    add("yyyy-MM-dd HH:mm");
                    add("yyyy-MM-dd HH:mm:ss");
                }};
                String value = source.trim();
                if ("".equals(value)) {
                    return null;
                }
                if (source.matches("^\\d{4}-\\d{1,2}$")) {
                    return parseDate(source, formarts.get(0));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                    return parseDate(source, formarts.get(1));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                    return parseDate(source, formarts.get(2));
                } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                    return parseDate(source, formarts.get(3));
                } else {
                    throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
                }
            }
            Date parseDate(String dateStr, String format) {
                Date date = null;
                try {
                    DateFormat dateFormat = new SimpleDateFormat(format);
                    date = (Date) dateFormat.parse(dateStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        });
    }
}
