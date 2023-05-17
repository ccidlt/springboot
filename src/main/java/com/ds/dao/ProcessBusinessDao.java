package com.ds.dao;

import com.ds.entity.ProcessBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程业务表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessBusinessDao extends BaseMapper<ProcessBusiness> {

    /**
     *  根据指定主键获取一条数据库记录, process_business
     */
    List<ProcessBusiness> selectAll(ProcessBusiness processBusiness);

    /**
     *  根据指定主键获取一条数据库记录, process_business
     */
    ProcessBusiness selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_business
     */
    int add(ProcessBusiness processBusiness);

    /**
     *  根据主键来更新符合条件的数据库记录, process_business
     */
    int updateByPrimaryKey(ProcessBusiness processBusiness);

    /**
     *  根据主键删除数据库的记录, process_business
     */
    int deleteByPrimaryKey(Long id);

}
