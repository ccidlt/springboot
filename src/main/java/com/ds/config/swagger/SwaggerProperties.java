package com.ds.config.swagger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger")
@RefreshScope
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerProperties {

    private Boolean enable;
    private String title;
    private String description;
    private String version;
    private String name;
    private String url;
    private String email;

}
