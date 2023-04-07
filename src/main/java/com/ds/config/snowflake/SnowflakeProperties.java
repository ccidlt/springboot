package com.ds.config.snowflake;

import lombok.Data;

@Data
public class SnowflakeProperties {
    //数据中心
    private long dataCenterId;
    //机器标识
    private long machineId;
}
