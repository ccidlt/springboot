package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormExecute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批执行表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormExecuteDao extends BaseMapper<FlowFormExecute> {

    /**
     *  获取数据库记录, flow_form_execute
     */
    List<FlowFormExecute> selectAll(FlowFormExecute flowFormExecute);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_execute
     */
    FlowFormExecute selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_execute
     */
    int add(FlowFormExecute flowFormExecute);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_execute
     */
    int updateByPrimaryKey(FlowFormExecute flowFormExecute);

    /**
     *  根据主键删除数据库的记录, flow_form_execute
     */
    int deleteByPrimaryKey(Long id);

}
