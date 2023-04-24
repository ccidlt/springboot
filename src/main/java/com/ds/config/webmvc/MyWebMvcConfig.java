package com.ds.config.webmvc;

import com.ds.config.perm.PermissionInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Bean
//    public RepeatSubmitInterceptor getRepeatSubmitInterceptor() {
//        return new RepeatSubmitInterceptor(stringRedisTemplate);
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludePathPatterns = {
                "/toLogin",
                "/login",
                "/error",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**",
                "/shiro/login", "/shiroToken/login"
        };
        registry.addInterceptor(logInterceptor())
                .addPathPatterns("/**")
                .order(0);
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns)
                .order(1);
        registry.addInterceptor(getPermissionInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns)
                .order(2);
//        registry.addInterceptor(getRepeatSubmitInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludePathPatterns)
//                .order(3);
    }

    /**
     * 配置静态资源
     *
     * @param registry
     */
    @Value("${filePath}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mytest/**").addResourceLocations("classpath:/mytest/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + filePath + "/");
    }

    /**
     * 配置请求视图映射
     *
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
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(resourceViewResolver());
    }

    /**
     * 跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowCredentials(true)
                .allowedOrigins("*");
    }

    /**
     * 后端返回给前端时间格式字符串，也可以用@JsonFormat
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(GlobalJsonDateConvert.instance);
        SimpleModule simpleModule = new SimpleModule();
        // Json Long --> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        converters.add(0, mappingJackson2HttpMessageConverter);
    }

    /**
     * 前端给后端时间字符串转换成Date，也可以用@DateTimeFormat
     *
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GlobalFormDateConvert());
    }
}
