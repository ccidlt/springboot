package com.ds.config.elasticsearch;

import com.ds.entity.elasticsearch.Elasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: Elasticsearch表示实体类的类型，String表示实体类中@Id属性的类型。
 * @Author lt
 * @Date 2023/5/29 15:36
 */
@Repository
public interface ElasticsearchDao extends ElasticsearchRepository<Elasticsearch, String> {
}
