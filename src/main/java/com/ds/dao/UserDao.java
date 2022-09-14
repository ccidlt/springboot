package com.ds.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ds.config.authority.DataScope;
import com.ds.entity.Boy;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@CacheNamespaceRef(name = "com.ds.dao.UserDao")
@CacheNamespace
public interface UserDao extends BaseMapper<Boy> {

    @DataScope(authKey = "0")
    List<Boy> getBoys();

    @Insert("insert into boy (id,name,create_time,update_time) values (#{id},#{name},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys=true, keyProperty = "id", keyColumn = "id")
    int insertBoy(Boy boy);

    List<Boy> getBoyDataPage();
}
