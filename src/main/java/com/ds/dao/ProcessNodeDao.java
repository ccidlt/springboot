package com.ds.dao;

import com.ds.entity.ProcessNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程节点表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessNodeDao extends BaseMapper<ProcessNode> {

    /**
     *  根据指定主键获取一条数据库记录, process_node
     */
    List<ProcessNode> selectAll(ProcessNode processNode);

    /**
     *  根据指定主键获取一条数据库记录, process_node
     */
    ProcessNode selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_node
     */
    int add(ProcessNode processNode);

    /**
     *  根据主键来更新符合条件的数据库记录, process_node
     */
    int updateByPrimaryKey(ProcessNode processNode);

    /**
     *  根据主键删除数据库的记录, process_node
     */
    int deleteByPrimaryKey(Long id);

}
