package com.ds.config.swagger;


import com.ds.enums.ResultCodeEnum;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * http://ip地址:端口/项目名/doc.html
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    private SwaggerProperties swaggerProperties;

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public Knife4jConfig(SwaggerProperties swaggerProperties,OpenApiExtensionResolver openApiExtensionResolver) {
        this.swaggerProperties = swaggerProperties;
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket defaultApi() {
        //添加head参数配置
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResultCodeEnum.values()).forEach(resultCode -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(resultCode.getCode()).message(resultCode.getValue()).responseModel(new ModelRef("Result")).build()
            );
        });

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .apiInfo(apiInfoDefault())
                .groupName("1.0")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.ds.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)// 全局配置
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .extensions(openApiExtensionResolver.buildExtensions("1.0"));
    }

    private ApiInfo apiInfoDefault() {
        Contact contact = new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail());
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(contact)
                .version(swaggerProperties.getVersion())
                .termsOfServiceUrl("/v2/api-docs")
                .build();
    }

    @Bean
    public Docket settingApi() {
        //添加head参数配置
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResultCodeEnum.values()).forEach(resultCode -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(resultCode.getCode()).message(resultCode.getValue()).responseModel(new ModelRef("Result")).build()
            );
        });

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .apiInfo(apiInfoDefault())
                .groupName("system")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.ds.controller.setting"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)// 全局配置
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .extensions(openApiExtensionResolver.buildExtensions("system"));
    }

    @Bean
    public Docket mqApi() {
        //添加head参数配置
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResultCodeEnum.values()).forEach(resultCode -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(resultCode.getCode()).message(resultCode.getValue()).responseModel(new ModelRef("Result")).build()
            );
        });

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .apiInfo(apiInfoDefault())
                .groupName("mq")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.ds.controller.mq"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)// 全局配置
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .extensions(openApiExtensionResolver.buildExtensions("mq"));
    }

    @Bean
    public Docket flowApi() {
        //添加head参数配置
        ParameterBuilder clientIdTicket = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        clientIdTicket.name("token").description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false).build(); //设置false，表示clientId参数 非必填,可传可不传！
        pars.add(clientIdTicket.build());

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Arrays.stream(ResultCodeEnum.values()).forEach(resultCode -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code(resultCode.getCode()).message(resultCode.getValue()).responseModel(new ModelRef("Result")).build()
            );
        });

        return new Docket(DocumentationType.SWAGGER_2) //swagger版本
                .pathMapping("/")
                .apiInfo(apiInfoDefault())
                .groupName("flow")
                .select()
                //扫描那些controller
                .apis(RequestHandlerSelectors.basePackage("com.ds.controller.flow"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)// 全局配置
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .extensions(openApiExtensionResolver.buildExtensions("flow"));
    }
}
