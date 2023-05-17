package com.ds.dao;

import com.ds.entity.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessDao extends BaseMapper<Process> {

    /**
     *  根据指定主键获取一条数据库记录, process
     */
    List<Process> selectAll(Process process);

    /**
     *  根据指定主键获取一条数据库记录, process
     */
    Process selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process
     */
    int add(Process process);

    /**
     *  根据主键来更新符合条件的数据库记录, process
     */
    int updateByPrimaryKey(Process process);

    /**
     *  根据主键删除数据库的记录, process
     */
    int deleteByPrimaryKey(Long id);

}
