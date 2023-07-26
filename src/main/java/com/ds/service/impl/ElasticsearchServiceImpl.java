package com.ds.service.impl;

import com.ds.config.elasticsearch.ElasticsearchDao;
import com.ds.entity.BusinessException;
import com.ds.entity.elasticsearch.Elasticsearch;
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
    public Boolean createIndexAndMapping(Class<?> classType) {
        if (elasticsearchRestTemplate.indexExists(classType))
            throw new BusinessException(1, "索引已存在");
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
    public Boolean existsIndex(Class<?> clazz) {
        return elasticsearchRestTemplate.indexExists(clazz);
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

    /**
     * termQuery：输入的查询内容是什么，就会按照什么去查询，并不会解析查询内容，对它分词
     * matchQuery：会将搜索词分词，再与目标查询字段进行匹配，若分词中的任意一个词与目标字段匹配上，则可查询到
     *
     *
     * 基本查询
     * 先来熟QueryBuilders这个类封装的基本查询
     * QueryBuilders.matchQuery() 会根据分词器进行分词，分词之后去查询
     * QueryBuilders.termQuery() 不会进行分词，且完全等于才会匹配
     * QueryBuilders.termsQuery() 一个字段匹配多个值，where name = ‘A’ or name = ‘B’
     * QueryBuilders.multiMatchQuery() 会分词 一个值对应多个字段 where username = ‘zs’ or password = ‘zs’
     * QueryBuilders.matchPhraseQuery() 不会分词，当成一个整体去匹配，相当于 %like%
     * 如果想使用一个字段匹配多个值，并且这多个值是and关系，如下 要求查询的数据中必须包含北京‘和’天津QueryBuilders.matchQuery(“address”,“北京 天津”).operator(Operator.AND)
     * 如果想使用一个字段匹配多个值，并且这多个值是or关系，如下 要求查询的数据中必须包含北京‘或’天津
     * QueryBuilders.matchQuery(“address”,“北京 天津”).operator(Operator.OR)
     *
     * 复合查询
     * 通常都是将多个查询条件组合在一起，常用的有must、must_not、should
     *
     *
     * @param key 字段
     * @param value 值
     * @return
     */
    @Override
    public List<Elasticsearch> search(String key, String value) {
        //分词查询(这里指定分词只是分传过来的参数)[它是要和es索引里的分词后数据一一对应才能返回]
        /*例子：前台传了 (山东省:粗粒度分为山东省 细粒度分为：山东省,山东,省)
            es索引库里(山东省济南市 粗粒度分为 山东省,济南市  细粒度分为:山东省,山东,省,济南市,济南,市)
            只有当前台分的词和后台分的词能有一个匹配上就可以*/
//        QueryBuilder queryBuilder = QueryBuilders.matchQuery(key, value).analyzer("ik_max_word");
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(key, value);
        Iterable<Elasticsearch> iterable = elasticsearchDao.search(queryBuilder);
        List<Elasticsearch> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

    /**
     * 分页
     * @param pageNo 当前页码0开始
     * @param pageSize 每页显示的条数
     * @param pageSize 字段
     * @param pageSize 值
     */
    @Override
    public List<Elasticsearch> searchByPage(Integer pageNo, Integer pageSize, String key, String value) {
        //设置查询分页
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        QueryBuilder queryBuilder = QueryBuilders.termQuery(key, value);
        Page<Elasticsearch> page = elasticsearchDao.search(queryBuilder, pageRequest);
        List<Elasticsearch> result = new ArrayList<>();
        page.forEach(result::add);
        return result;
    }

}
