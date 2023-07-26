package com.ds.config.mongodb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Mongodb配置
 * @author lt
 * @date 2023/7/26 10:47
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.ds.config.mongodb")
public class MongoConfiguration {
}
