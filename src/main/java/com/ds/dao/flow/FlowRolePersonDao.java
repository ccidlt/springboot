package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowRolePerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 根据角色配置人员 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowRolePersonDao extends BaseMapper<FlowRolePerson> {

    /**
     *  获取数据库记录, flow_role_person
     */
    List<FlowRolePerson> selectAll(FlowRolePerson flowRolePerson);

    /**
     *  根据指定主键获取一条数据库记录, flow_role_person
     */
    FlowRolePerson selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_role_person
     */
    int add(FlowRolePerson flowRolePerson);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_role_person
     */
    int updateByPrimaryKey(FlowRolePerson flowRolePerson);

    /**
     *  根据主键删除数据库的记录, flow_role_person
     */
    int deleteByPrimaryKey(Long id);

}
