package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批任务待办表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormTaskDao extends BaseMapper<FlowFormTask> {

    /**
     *  获取数据库记录, flow_form_task
     */
    List<FlowFormTask> selectAll(FlowFormTask flowFormTask);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_task
     */
    FlowFormTask selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_task
     */
    int add(FlowFormTask flowFormTask);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_task
     */
    int updateByPrimaryKey(FlowFormTask flowFormTask);

    /**
     *  根据主键删除数据库的记录, flow_form_task
     */
    int deleteByPrimaryKey(Long id);

}
