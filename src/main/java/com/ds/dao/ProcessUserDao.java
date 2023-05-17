package com.ds.dao;

import com.ds.entity.ProcessUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程用户表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessUserDao extends BaseMapper<ProcessUser> {

    /**
     *  根据指定主键获取一条数据库记录, process_user
     */
    List<ProcessUser> selectAll(ProcessUser processUser);

    /**
     *  根据指定主键获取一条数据库记录, process_user
     */
    ProcessUser selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_user
     */
    int add(ProcessUser processUser);

    /**
     *  根据主键来更新符合条件的数据库记录, process_user
     */
    int updateByPrimaryKey(ProcessUser processUser);

    /**
     *  根据主键删除数据库的记录, process_user
     */
    int deleteByPrimaryKey(Long id);

}
