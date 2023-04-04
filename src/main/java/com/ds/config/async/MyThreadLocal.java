package com.ds.config.async;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyThreadLocal {

    private static final ThreadLocal<SqlSession> threadSession = new ThreadLocal<>();

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    private static SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void init(){
        sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static SqlSession getSession() throws Exception {
        SqlSession ss = threadSession.get();
        try {
            if (ss == null) {
                ss = getSqlSessionFactory().openSession();
                threadSession.set(ss);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return ss;
    }

}
