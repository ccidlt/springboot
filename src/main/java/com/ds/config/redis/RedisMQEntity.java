package com.ds.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redislist")
@Data
public class RedisMQEntity {

    private String mq1;

    private String mq2;

    private String mq3;

}
