package com.ds.dao;

import com.ds.entity.ProcessLine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程线表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessLineDao extends BaseMapper<ProcessLine> {

    /**
     *  根据指定主键获取一条数据库记录, process_line
     */
    List<ProcessLine> selectAll(ProcessLine processLine);

    /**
     *  根据指定主键获取一条数据库记录, process_line
     */
    ProcessLine selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_line
     */
    int add(ProcessLine processLine);

    /**
     *  根据主键来更新符合条件的数据库记录, process_line
     */
    int updateByPrimaryKey(ProcessLine processLine);

    /**
     *  根据主键删除数据库的记录, process_line
     */
    int deleteByPrimaryKey(Long id);

}
