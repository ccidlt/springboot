package com.ds.config.mybatisplus;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SqlContext {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSession getSqlSession(){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }

    public SqlSession getSqlSession(ExecutorType executorType, boolean isCommitAuto){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession(executorType, isCommitAuto);
    }
}
