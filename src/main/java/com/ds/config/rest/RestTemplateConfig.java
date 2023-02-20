package com.ds.config.rest;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ds.entity.Result;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}

class ExceptionUtil {
    public Result handleException(BlockException exception) {
        return Result.build(503, exception.getClass().getCanonicalName()+"\t 服务不可用");
    }
}
