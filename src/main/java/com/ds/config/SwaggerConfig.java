package com.ds.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        //http://ip地址:端口/项目名/swagger-ui.html#/
        //http://ip地址:端口/项目名/doc.html
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("springboot") //网站标题
                .description("测试项目") //网站描述
                .version("1.0") //版本
                .contact(new Contact("lt", "", "635971082@qq.com")) //联系人
                .build();

        //添加head参数配置
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.ds.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}
