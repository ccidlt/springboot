package com.ds.config.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    @ConfigurationProperties(prefix = "snow-flake")
    public SnowflakeProperties snowflakeProperties() {
        return new SnowflakeProperties();
    }

    @Bean
    public Snowflake snowFlake() {
        SnowflakeProperties snowflakeProperties = snowflakeProperties();
        return new Snowflake(snowflakeProperties.getDataCenterId(), snowflakeProperties.getMachineId());
    }

    @Bean
    public SnowflakeManager snowflakeManager() {
        SnowflakeProperties snowflakeProperties = snowflakeProperties();
        return new SnowflakeManager(snowflakeProperties.getMachineId(), snowflakeProperties.getDataCenterId());
    }

}
