package com.ds.service.impl;

import com.ds.entity.Process;
import com.ds.dao.ProcessDao;
import com.ds.service.ProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程表 服务实现类
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessDao, Process> implements ProcessService {

}
