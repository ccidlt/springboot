package com.ds.config.snowflake;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author lt
 * @description:
 * @date 2024/1/19 15:15
 */
@Component
public class UniqueIdGenerator {

    private static final String DATACENTER_ID = "snowflake:datacenter_id";
    private static final String MACHINE_ID = "snowflake:machine_id";

    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    private ValueOperations<String, Long> valueOperations;

    @PostConstruct
    public void init() {
        this.valueOperations = redisTemplate.opsForValue();
    }

    public long allocateDatacenterId() {
        long datacenterId = valueOperations.increment(DATACENTER_ID, 1) % 32; // 取模操作，确保在0-31之间
        if (datacenterId == 1) {
            // 第一次分配，设置过期时间为永久
            redisTemplate.persist(DATACENTER_ID);
        }
        return datacenterId;
    }

    public long allocateMachineId(long datacenterId) {
        long machineId = valueOperations.increment(MACHINE_ID, 1) % 32; // 取模操作，确保在0-31之间
        if (machineId == 1) {
            // 第一次分配，设置过期时间为永久
            redisTemplate.persist(MACHINE_ID);
        }
        return machineId;
    }

}
