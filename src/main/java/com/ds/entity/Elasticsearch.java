package com.ds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/29 10:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 注解：@Document用来声明Java对象与ElasticSearch索引的关系
 * indexName 索引名称
 * type      索引类型
 * shards    主分区数量，默认5
 * replicas  副本分区数量，默认1
 * createIndex 索引不存在时，是否自动创建索引，默认true
 */
@Document(indexName = "es", type = "_doc", replicas = 1, shards = 1, createIndex = true)
public class Elasticsearch {
    // @Id springboot-jpa定义了一组Java类和注解，可以将Java对象映射到数据库表，从而方便地进行CRUD操作
    @Id
    @Field(index = true, store = true, type = FieldType.Keyword)
    private String id;

    @Field(index = true, store = true, type = FieldType.Keyword)
    private String name;

    @Field(index = true, store = true, type = FieldType.Text, analyzer = "ik_smart")
    //Text可以分词 ik_smart=粗粒度分词 ik_max_word 为细粒度分词
    private String address;

    @Field(index = false, store = true, type = FieldType.Integer)
    private Integer age;

    @Field(index = false, store = true, type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date createTime;

}
