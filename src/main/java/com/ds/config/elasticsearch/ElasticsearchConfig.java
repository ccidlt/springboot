package com.ds.config.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: ES搜索引擎配置类
 * @Author lt
 * @Date 2023/4/18 16:40
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String url;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder builder = RestClient.builder(HttpHost.create(url));
        return new RestHighLevelClient(builder);
    }

}
