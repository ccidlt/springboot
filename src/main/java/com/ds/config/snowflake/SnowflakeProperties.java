package com.ds.config.snowflake;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "snow-flake")
@RefreshScope
public class SnowflakeProperties {
    //数据中心
    private long dataCenterId;
    //机器标识
    private long machineId;
}
