package com.ds;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Druid连接池：需要排除掉 DruidDataSourceAutoConfigure 类，不然启动会报错找不到配置的 url
//com.gitee.sunchenbin.mybatis.actable 根据实体类自动建表
@SpringBootApplication(
        exclude = DruidDataSourceAutoConfigure.class,
        scanBasePackages = {"com.ds.*", "com.gitee.sunchenbin.mybatis.actable.manager.*"}
)
@MapperScan(basePackages = {"com.ds.dao", "com.gitee.sunchenbin.mybatis.actable.dao.*"})
@EnableAsync
@EnableScheduling
@Slf4j
@EnableFeignClients(basePackages = {"com.ds.service"})
@ServletComponentScan//检索@WebFilter、@WebListener
@EnableSwagger2
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class SpringbootApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(SpringbootApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}{}{}/doc.html\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path", ""),
                env.getProperty("spring.mvc.servlet.path", "")
        );
    }

}
