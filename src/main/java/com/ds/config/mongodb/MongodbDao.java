package com.ds.config.mongodb;

import com.ds.entity.mongodb.Mongodb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lt
 * @date 2023/7/26 14:10
 */
@Repository
public interface MongodbDao extends MongoRepository<Mongodb,String> {
}
