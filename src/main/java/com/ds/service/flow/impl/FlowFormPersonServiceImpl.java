package com.ds.service.flow.impl;

import com.ds.entity.flow.entity.FlowFormPerson;
import com.ds.dao.flow.FlowFormPersonDao;
import com.ds.service.flow.FlowFormPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批人员表 服务实现类
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Service
public class FlowFormPersonServiceImpl extends ServiceImpl<FlowFormPersonDao, FlowFormPerson> implements FlowFormPersonService {

}
