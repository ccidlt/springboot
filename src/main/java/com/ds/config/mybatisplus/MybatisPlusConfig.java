package com.ds.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ds.config.authority.DataScopeInnerInterceptor;
import com.ds.config.snowflake.Snowflake;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class MybatisPlusConfig implements MetaObjectHandler, IdentifierGenerator {

    /*//自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("isdelete", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }

    //page对象分页
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    //乐观锁
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    //逻辑删除
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }*/

    @Autowired
    private Snowflake snowflake;

    @Override
    public void insertFill(MetaObject metaObject) {
        //如果属性有值则不覆盖,如果填充值为null则不填充.
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, "isdelete", Integer.class, 0);
        this.strictInsertFill(metaObject, "isDelete", Integer.class, 0);
        this.strictInsertFill(metaObject, "isDisable", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        //无论属性是否有值都填充
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(dataScopeInnerInterceptor());
        //乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //防全表更新与删除
        //interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    @Bean
    public DataScopeInnerInterceptor dataScopeInnerInterceptor() {
        return new DataScopeInnerInterceptor();
    }

    @Override
    public Number nextId(Object entity) {
        return snowflake.nextId();
    }

    /**
     * 配置Mybatis枚举
     * @return
     */
    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class); // 枚举处理类
            properties.setConfiguration(configuration);
        };
    }

}

