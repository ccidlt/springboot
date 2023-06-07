package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批单信息表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormInfoDao extends BaseMapper<FlowFormInfo> {

    /**
     *  获取数据库记录, flow_form_info
     */
    List<FlowFormInfo> selectAll(FlowFormInfo flowFormInfo);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_info
     */
    FlowFormInfo selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_info
     */
    int add(FlowFormInfo flowFormInfo);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_info
     */
    int updateByPrimaryKey(FlowFormInfo flowFormInfo);

    /**
     *  根据主键删除数据库的记录, flow_form_info
     */
    int deleteByPrimaryKey(Long id);

}
