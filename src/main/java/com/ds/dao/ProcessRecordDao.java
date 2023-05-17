package com.ds.dao;

import com.ds.entity.ProcessRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程记录表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessRecordDao extends BaseMapper<ProcessRecord> {

    /**
     *  根据指定主键获取一条数据库记录, process_record
     */
    List<ProcessRecord> selectAll(ProcessRecord processRecord);

    /**
     *  根据指定主键获取一条数据库记录, process_record
     */
    ProcessRecord selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_record
     */
    int add(ProcessRecord processRecord);

    /**
     *  根据主键来更新符合条件的数据库记录, process_record
     */
    int updateByPrimaryKey(ProcessRecord processRecord);

    /**
     *  根据主键删除数据库的记录, process_record
     */
    int deleteByPrimaryKey(Long id);

}
