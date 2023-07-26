package com.ds.dao;

import com.ds.entity.elasticsearch.Elasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/29 15:36
 */
@Repository
public interface ElasticsearchDao extends ElasticsearchRepository<Elasticsearch, String> {
}
