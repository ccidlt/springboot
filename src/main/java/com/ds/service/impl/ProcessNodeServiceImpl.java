package com.ds.service.impl;

import com.ds.entity.ProcessNode;
import com.ds.dao.ProcessNodeDao;
import com.ds.service.ProcessNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程节点表 服务实现类
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Service
public class ProcessNodeServiceImpl extends ServiceImpl<ProcessNodeDao, ProcessNode> implements ProcessNodeService {

}
