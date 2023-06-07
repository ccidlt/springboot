package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批记录表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormRecordDao extends BaseMapper<FlowFormRecord> {

    /**
     *  获取数据库记录, flow_form_record
     */
    List<FlowFormRecord> selectAll(FlowFormRecord flowFormRecord);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_record
     */
    FlowFormRecord selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_record
     */
    int add(FlowFormRecord flowFormRecord);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_record
     */
    int updateByPrimaryKey(FlowFormRecord flowFormRecord);

    /**
     *  根据主键删除数据库的记录, flow_form_record
     */
    int deleteByPrimaryKey(Long id);

}
