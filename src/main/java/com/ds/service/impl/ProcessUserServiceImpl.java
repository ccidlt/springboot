package com.ds.service.impl;

import com.ds.entity.ProcessUser;
import com.ds.dao.ProcessUserDao;
import com.ds.service.ProcessUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程用户表 服务实现类
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Service
public class ProcessUserServiceImpl extends ServiceImpl<ProcessUserDao, ProcessUser> implements ProcessUserService {

}
