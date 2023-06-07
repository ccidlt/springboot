package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 主体信息表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowInfoDao extends BaseMapper<FlowInfo> {

    /**
     *  获取数据库记录, flow_info
     */
    List<FlowInfo> selectAll(FlowInfo flowInfo);

    /**
     *  根据指定主键获取一条数据库记录, flow_info
     */
    FlowInfo selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_info
     */
    int add(FlowInfo flowInfo);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_info
     */
    int updateByPrimaryKey(FlowInfo flowInfo);

    /**
     *  根据主键删除数据库的记录, flow_info
     */
    int deleteByPrimaryKey(Long id);

}
