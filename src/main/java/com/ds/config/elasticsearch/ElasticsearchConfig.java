package com.ds.config.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @Description: ES搜索引擎配置类
 * @Author lt
 * @Date 2023/4/18 16:40
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ds.entity.elasticsearch")
public class ElasticsearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String url;
    @Value("${spring.elasticsearch.rest.host}")
    private String host;
    @Value("${spring.elasticsearch.rest.port}")
    private String port;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestClientBuilder builder = RestClient.builder(HttpHost.create(url));
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host,Integer.valueOf(port),HttpHost.DEFAULT_SCHEME_NAME));
        return new RestHighLevelClient(builder);
    }

}
