package com.ds.config.async;

import com.ds.SpringbootApplication;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class MyThreadLocal {

    private static final ThreadLocal<SqlSession> threadSession = new ThreadLocal<>();

    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 属性注入
     */
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @PostConstruct
//    public void init(){
//        sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
//    }

    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * setter注入
     * @param sqlSessionTemplate
     */
//    @Autowired
//    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//        this.sqlSessionTemplate = sqlSessionTemplate;
//        MyThreadLocal.sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
//    }

    /**
     * 构造器注入
     * @param sqlSessionTemplate
     */
    @Autowired
    public MyThreadLocal(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        MyThreadLocal.sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
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

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        System.out.println(getSqlSessionFactory());
    }

}
