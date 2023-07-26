package com.ds.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 *
 * 原版本的String先改为Text和Keyword，Text支持分词
 *
 */
@Document(indexName = "testindex", createIndex = false)
public class Elasticsearch {
    // @Id springboot-jpa定义了一组Java类和注解，可以将Java对象映射到数据库表，从而方便地进行CRUD操作
    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    //Text可以分词 ik_smart=粗粒度分词 ik_max_word 为细粒度分词
    private String address;

    @Field(index = false, type = FieldType.Integer)
    private Integer age;

    @Field(index = false, type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
