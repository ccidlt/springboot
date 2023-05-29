package com.ds.controller;

import com.ds.entity.Elasticsearch;
import com.ds.service.ElasticsearchService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Description: elasticsearch搜索引擎
 * @Author lt
 * @Date 2023/5/29 10:39
 */
@Api(tags = "elasticsearch搜索引擎操作", hidden = true)
@ApiIgnore
@RestController
@RequestMapping("es")
public class ElasticsearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    /**
     * @param classType : 要创建es的索引及映射(一定要通过这种方法创建，否则都是创建出来的映射都是默认的)基于传入对象中的@Document注解
     * @return boolean
     * @throws
     * @Author lt
     * @Date 2021/12/9 10:43
     */
    @PostMapping("createIndexAndMapping")
    public boolean createIndexAndMapping(Class<?> classType) {
        return elasticsearchService.createIndexAndMapping(classType);
    }

    /**
     * @param clazz : 删除的哪个索引(从传入对象中的@Document注解中indexName属性获取)
     * @return java.lang.Boolean
     * @explain : 删除索引
     * @Author lt
     * @Date 2021/12/27 14:27
     */
    @PostMapping("deleteIndex")
    public Boolean deleteIndex(Class<?> clazz) {
        return elasticsearchService.deleteIndex(clazz);
    }

    @PostMapping("save")
    public Elasticsearch save(Elasticsearch elasticsearch) {
        return elasticsearchService.save(elasticsearch);
    }

    @PostMapping("saveAll")
    public List<Elasticsearch> saveAll(List<Elasticsearch> elasticsearchList) {
        return elasticsearchService.saveAll(elasticsearchList);
    }

    @PostMapping("deleteById")
    public void deleteById(String id) {
        elasticsearchService.deleteById(id);
    }

    @PostMapping("delete")
    public void delete(Elasticsearch elasticsearch) {
        elasticsearchService.delete(elasticsearch);
    }

    @PostMapping("deleteAll")
    public void deleteAll() {
        elasticsearchService.deleteAll();
    }

    @PostMapping("findById")
    public Elasticsearch findById(String id){
        return elasticsearchService.findById(id);
    }

    @PostMapping("findAll")
    public List<Elasticsearch> findAll(){
        return elasticsearchService.findAll();
    }

    @PostMapping("search")
    public List<Elasticsearch> search(String key, String value) {
        return elasticsearchService.search(key, value);
    }

}
