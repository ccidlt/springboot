package com.ds.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ds.config.authority.DataScope;
import com.ds.entity.Boy;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@CacheNamespaceRef(name = "com.ds.dao.boyDao")
@CacheNamespace
public interface BoyDao extends BaseMapper<Boy> {

    @DataScope
    List<Boy> getBoys();

    @Insert("insert into boy (id,name,create_time,update_time) values (#{id},#{name},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertBoy(Boy boy);

    List<Boy> getBoyDataPage();

    @DataScope
    List<Boy> selectList(@Param("ew") Wrapper<Boy> queryWrapper);
}
