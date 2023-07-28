package com.ds.service.impl;

import com.ds.config.mongodb.MongodbDao;
import com.ds.entity.mongodb.Mongodb;
import com.ds.service.MongodbService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mongodb操作
 * @author lt
 * @date 2023/7/26 14:08
 */
@Service
public class MongodbServiceImpl implements MongodbService {

    @Resource
    private MongodbDao mongodbDao;

    @Override
    public Mongodb insert(Mongodb mongodb) {
        return mongodbDao.insert(mongodb);
    }

    @Override
    public Mongodb update(Mongodb mongodb) {
        return mongodbDao.save(mongodb);
    }

    @Override
    public void delete(String id) {
        mongodbDao.deleteById(id);
    }

    @Override
    public List<Mongodb> findAll() {
        return mongodbDao.findAll();
    }

    @Override
    public Mongodb findById(String id) {
        return mongodbDao.findById(id).orElse(null);
    }

    @Override
    public List<Mongodb> findByName(String name) {
//        return mongodbDao.findByName(name);
        Mongodb mongodb = new Mongodb();
        mongodb.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        Example<Mongodb> example = Example.of(mongodb, matcher);
        return mongodbDao.findAll(example);
    }

    @Override
    public Page<Mongodb> findByNameForPage(String name, Integer pageNo, Integer pageSize) {
        Mongodb mongodb = new Mongodb();
        mongodb.setName(name);
        //对name前后模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("id", "pageNum", "pageSize");
        Example<Mongodb> example = Example.of(mongodb, matcher);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNo-1, pageSize, sort);
        return mongodbDao.findAll(example,pageRequest);
    }
}
