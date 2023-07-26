package com.ds.controller;

import com.ds.entity.mongodb.Mongodb;
import com.ds.service.MongodbService;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mongodb操作
 * @author lt
 * @date 2023/7/26 14:00
 */
@Api(tags = {"Mongodb操作"},hidden = true)
@ApiIgnore
@RestController
@RequestMapping("mongodb")
public class MongodbController {

    @Resource
    private MongodbService mongodbService;

    /**
     * 新增
     * @param mongodb 入参
     * @return 返回值
     */
    @PostMapping("save")
    public Mongodb save(Mongodb mongodb) {
        return mongodbService.save(mongodb);
    }

    /**
     * 删除
     * @param id 主键
     */
    @GetMapping("delete/{id}")
    public void delete(@PathVariable String id) {
        mongodbService.delete(id);
    }

    /**
     * 查询所有
     * @return 返回值
     */
    @GetMapping("findAll")
    public List<Mongodb> findAll() {
        return mongodbService.findAll();
    }

    /**
     * 查询
     * @param id 主键
     * @return 返回值
     */
    @GetMapping("findById/{id}")
    public Mongodb findById(@PathVariable String id) {
        return mongodbService.findById(id);
    }

    /**
     * 查询
     * @param name 名称
     * @return 返回值
     */
    @GetMapping("findByName/{name}")
    public List<Mongodb> findByName(@PathVariable String name) {
        return mongodbService.findByName(name);
    }

    /**
     * 分页
     * @param name 名称
     * @return 返回值
     */
    @GetMapping("findByNameForPage/{name}/{pageNo}/{pageSize}")
    public Page<Mongodb> findByNameForPage(@PathVariable String name, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return mongodbService.findByNameForPage(name,pageNo,pageSize);
    }

}
