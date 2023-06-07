package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 角色配置表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowRoleDao extends BaseMapper<FlowRole> {

    /**
     *  获取数据库记录, flow_role
     */
    List<FlowRole> selectAll(FlowRole flowRole);

    /**
     *  根据指定主键获取一条数据库记录, flow_role
     */
    FlowRole selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_role
     */
    int add(FlowRole flowRole);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_role
     */
    int updateByPrimaryKey(FlowRole flowRole);

    /**
     *  根据主键删除数据库的记录, flow_role
     */
    int deleteByPrimaryKey(Long id);

}
