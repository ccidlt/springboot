package com.ds.dao.flow;

import com.ds.entity.flow.entity.FlowFormPerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.*;

/**
 * <p>
 * 审批人员表 Mapper 接口
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
public interface FlowFormPersonDao extends BaseMapper<FlowFormPerson> {

    /**
     *  获取数据库记录, flow_form_person
     */
    List<FlowFormPerson> selectAll(FlowFormPerson flowFormPerson);

    /**
     *  根据指定主键获取一条数据库记录, flow_form_person
     */
    FlowFormPerson selectByPrimaryKey(Long id);

    /**
     *  新写入数据库记录, flow_form_person
     */
    int add(FlowFormPerson flowFormPerson);

    /**
     *  根据主键来更新符合条件的数据库记录, flow_form_person
     */
    int updateByPrimaryKey(FlowFormPerson flowFormPerson);

    /**
     *  根据主键删除数据库的记录, flow_form_person
     */
    int deleteByPrimaryKey(Long id);

}
