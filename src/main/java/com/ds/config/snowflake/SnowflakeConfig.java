package com.ds.config.snowflake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class SnowflakeConfig {

//    @Bean
//    @ConfigurationProperties(prefix = "snow-flake")
//    public SnowflakeProperties snowflakeProperties() {
//        return new SnowflakeProperties();
//    }

    @Resource
    private SnowflakeProperties snowflakeProperties;

    @Bean
    public Snowflake snowFlake() {
        return new Snowflake(snowflakeProperties.getDataCenterId(), snowflakeProperties.getMachineId());
    }

    @Bean
    public SnowflakeManager snowflakeManager() {
        return new SnowflakeManager(snowflakeProperties.getMachineId(), snowflakeProperties.getDataCenterId());
    }

}
