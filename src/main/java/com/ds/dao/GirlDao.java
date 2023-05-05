package com.ds.dao;

import com.ds.entity.Girl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-05
 */
public interface GirlDao extends BaseMapper<Girl> {

    /**
     *  根据指定主键获取一条数据库记录, girl
     */
    List<Girl> select(Girl girl);

    /**
     *  根据指定主键获取一条数据库记录, girl
     */
    Girl selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, girl
     */
    int insert(Girl girl);

    /**
     *  根据主键来更新符合条件的数据库记录, girl
     */
    int updateByPrimaryKey(Girl girl);

    /**
     *  根据主键删除数据库的记录, girl
     */
    int deleteByPrimaryKey(Long id);

}
