package com.ds.config.webmvc;

import com.ds.config.fileprogress.CustomMultipartResolver;
import com.ds.config.perm.PermissionInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

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

    /**
     * 指定自定义解析器
     * 将 multipartResolver 指向我们刚刚创建好的继承 CustomMultipartResolver 类的 自定义文件上传处理类
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        return new CustomMultipartResolver();
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
     * 自定义类型转换, @RequestBody参数格式化
     * @param converters
     */
    /**
     * Date格式化字符串
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * DateTime格式化字符串
     */
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Time格式化字符串
     */
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置日期格式
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //@RequestBody前端向后端或后端向前端日期传参格式化
        //方式一
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule simpleModule = new SimpleModule();
        //方式二
//        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
//            @Override
//            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider
//                    serializerProvider) throws IOException {
//                //将Date类型的转化为字符串
//                if(date != null){
//                    jsonGenerator.writeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//                }else{
//                    jsonGenerator.writeNull();
//                }
//            }
//        });
//        simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
//            @Override
//            public Date deserialize(JsonParser jsonParser, DeserializationContext
//                    deserializationContext) throws IOException {
//                try {
//                    if(StrUtil.isNotBlank(jsonParser.getText())){
//                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonParser.getText());
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
        // Json Long --> String 避免雪花等生成的id过长到前端自动截断
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        // 浮点型使用字符串
        simpleModule.addSerializer(Double.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Double.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        // java8 时间格式化
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME_FORMAT));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMAT));
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMAT));
        objectMapper.registerModule(simpleModule);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        // 处理中文乱码问题
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(ImmutableList.of(MediaType.APPLICATION_JSON_UTF8));
        converters.add(0,mappingJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     * 自定义类型转换, @RequestParam/@PathVariable的日期字符串转换对应日期类型
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GlobalFormDateConvert());
    }

}
