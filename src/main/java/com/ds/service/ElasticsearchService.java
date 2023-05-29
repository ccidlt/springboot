package com.ds.service;

import com.ds.entity.Elasticsearch;

import java.util.List;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/29 10:41
 */
public interface ElasticsearchService {
    /**
     * @param classType : 要创建es的索引及映射(一定要通过这种方法创建，否则都是创建出来的映射都是默认的)基于传入对象中的@Document注解
     * @return boolean
     * @throws
     * @Author lt
     * @Date 2021/12/9 10:43
     */
    public boolean createIndexAndMapping(Class<?> classType);

    /**
     * @param clazz : 删除的哪个索引(从传入对象中的@Document注解中indexName属性获取)
     * @return java.lang.Boolean
     * @explain : 删除索引
     * @Author lt
     * @Date 2021/12/27 14:27
     */
    public Boolean deleteIndex(Class<?> clazz);

    public Elasticsearch save(Elasticsearch elasticsearch);

    public List<Elasticsearch> saveAll(List<Elasticsearch> elasticsearchList);

    public void deleteById(String id);

    public void delete(Elasticsearch elasticsearch);

    public void deleteAll();

    public Elasticsearch findById(String id);

    public List<Elasticsearch> findAll();

    public void findAllByPage(Integer pageNum, Integer pageSize);

    public List<Elasticsearch> search(String key, String value);

    public void searchByPage(Integer pageNum, Integer pageSize, String key, String value);

}
