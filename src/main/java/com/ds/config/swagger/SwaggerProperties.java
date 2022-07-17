package com.ds.config.swagger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private String name;
    private String url;
    private String email;

}
