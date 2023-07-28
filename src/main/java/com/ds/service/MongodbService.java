package com.ds.service;

import com.ds.entity.mongodb.Mongodb;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mongodb操作
 * @author lt
 * @date 2023/7/26 14:07
 */
public interface MongodbService {
    Mongodb insert(Mongodb mongodb);

    Mongodb update(Mongodb mongodb);

    void delete(String id);

    List<Mongodb> findAll();

    Mongodb findById(String id);

    List<Mongodb> findByName(String name);

    Page<Mongodb> findByNameForPage(String name, Integer pageNo, Integer pageSize);
}
