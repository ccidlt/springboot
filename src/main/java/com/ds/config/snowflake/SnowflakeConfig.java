package com.ds.config.snowflake;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    @ConfigurationProperties(prefix = "snow-flake")
    @RefreshScope
    public SnowflakeProperties snowflakeProperties() {
        return new SnowflakeProperties();
    }

    @Bean
    public Snowflake snowFlake() {
        return new Snowflake(snowflakeProperties().getDataCenterId(), snowflakeProperties().getMachineId());
    }

    @Bean
    public SnowflakeManager snowflakeManager() {
        return new SnowflakeManager(snowflakeProperties().getMachineId(), snowflakeProperties().getDataCenterId());
    }

}
