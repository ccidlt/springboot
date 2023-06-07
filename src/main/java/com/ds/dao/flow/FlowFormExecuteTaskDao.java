package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormExecuteTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批执行任务表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormExecuteTaskDao extends BaseMapper<FlowFormExecuteTask> {

    /**
     *  获取数据库记录, flow_form_execute_task
     */
    List<FlowFormExecuteTask> selectAll(FlowFormExecuteTask flowFormExecuteTask);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_execute_task
     */
    FlowFormExecuteTask selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_execute_task
     */
    int add(FlowFormExecuteTask flowFormExecuteTask);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_execute_task
     */
    int updateByPrimaryKey(FlowFormExecuteTask flowFormExecuteTask);

    /**
     *  根据主键删除数据库的记录, flow_form_execute_task
     */
    int deleteByPrimaryKey(Long id);

}
