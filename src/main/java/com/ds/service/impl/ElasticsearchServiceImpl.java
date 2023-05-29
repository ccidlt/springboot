package com.ds.service.impl;

import com.ds.dao.ElasticsearchDao;
import com.ds.entity.Elasticsearch;
import com.ds.service.ElasticsearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author lt
 * @Date 2023/5/29 10:42
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private ElasticsearchDao elasticsearchDao;
    /**
     * @param classType : 要创建es的索引及映射(一定要通过这种方法创建，否则都是创建出来的映射都是默认的)基于传入对象中的@Document注解
     * @return boolean
     * @throws
     * @Author lt
     * @Date 2021/12/9 10:43
     */
    @Override
    public boolean createIndexAndMapping(Class<?> classType) {
        if (elasticsearchRestTemplate.indexExists(classType))
            return true;
        boolean index = elasticsearchRestTemplate.createIndex(classType);
        boolean mapping = elasticsearchRestTemplate.putMapping(classType);
        return index && mapping;
    }

    /**
     * @param clazz : 删除的哪个索引(从传入对象中的@Document注解中indexName属性获取)
     * @return java.lang.Boolean
     * @explain : 删除索引
     * @Author lt
     * @Date 2021/12/27 14:27
     */
    @Override
    public Boolean deleteIndex(Class<?> clazz) {
        return elasticsearchRestTemplate.deleteIndex(clazz);
    }

    @Override
    public Elasticsearch save(Elasticsearch elasticsearch) {
        return elasticsearchDao.save(elasticsearch);
    }

    @Override
    public List<Elasticsearch> saveAll(List<Elasticsearch> elasticsearchList) {
        Iterable<Elasticsearch> iterable = elasticsearchDao.saveAll(elasticsearchList);
        List<Elasticsearch> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    @Override
    public void deleteById(String id) {
        elasticsearchDao.deleteById(id);
    }

    @Override
    public void delete(Elasticsearch elasticsearch) {
        elasticsearchDao.delete(elasticsearch);
    }

    @Override
    public void deleteAll() {
        elasticsearchDao.deleteAll();
    }

    @Override
    public Elasticsearch findById(String id) {
        return elasticsearchDao.findById(id).orElse(null);
    }

    @Override
    public List<Elasticsearch> findAll() {
        Iterable<Elasticsearch> iterable = elasticsearchDao.findAll();
        List<Elasticsearch> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    /**
     * 分页
     * @param pageNum 当前页码0开始
     * @param pageSize 每页显示的条数
     */
    @Override
    public void findAllByPage(Integer pageNum, Integer pageSize) {
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        //设置分页
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort);
        //分页查询
        Page<Elasticsearch> page = elasticsearchDao.findAll(pageRequest);
        page.forEach(System.out::println);
    }

    @Override
    public List<Elasticsearch> search(String key, String value) {
        //分词查询(这里指定分词只是分传过来的参数)[它是要和es索引里的分词后数据一一对应才能返回]
        /*例子：前台传了 (山东省:粗粒度分为山东省 细粒度分为：山东省,山东,省)
            es索引库里(山东省济南市 粗粒度分为 山东省,济南市  细粒度分为:山东省,山东,省,济南市,济南,市)
            只有当前台分的词和后台分的词能有一个匹配上就可以*/
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(key, value).analyzer("ik_smart");
        Iterable<Elasticsearch> iterable = elasticsearchDao.search(queryBuilder);
        List<Elasticsearch> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    /**
     * 分页
     * @param pageNum 当前页码0开始
     * @param pageSize 每页显示的条数
     * @param pageSize 字段
     * @param pageSize 值
     */
    @Override
    public void searchByPage(Integer pageNum, Integer pageSize, String key, String value) {
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(key, value).analyzer("ik_smart");
        Iterable<Elasticsearch> iterable = elasticsearchDao.search(queryBuilder, pageRequest);
        for (Elasticsearch elasticsearch : iterable) {
            System.out.println(elasticsearch);
        }
    }

}
