package com.ds.dao;

import com.ds.entity.ProcessApproval;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 流程审批人表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
public interface ProcessApprovalDao extends BaseMapper<ProcessApproval> {

    /**
     *  根据指定主键获取一条数据库记录, process_approval
     */
    List<ProcessApproval> selectAll(ProcessApproval processApproval);

    /**
     *  根据指定主键获取一条数据库记录, process_approval
     */
    ProcessApproval selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, process_approval
     */
    int add(ProcessApproval processApproval);

    /**
     *  根据主键来更新符合条件的数据库记录, process_approval
     */
    int updateByPrimaryKey(ProcessApproval processApproval);

    /**
     *  根据主键删除数据库的记录, process_approval
     */
    int deleteByPrimaryKey(Long id);

}
