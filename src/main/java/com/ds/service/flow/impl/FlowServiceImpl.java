package com.ds.service.flow.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ds.entity.Result;
import com.ds.entity.flow.dto.FlowDraftDTO;
import com.ds.entity.flow.entity.FlowInfo;
import com.ds.service.flow.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 15:19
 */
@Service
public class FlowServiceImpl implements FlowService {

    @Resource
    private FlowInfoService flowInfoService;
    @Resource
    private FlowFormInfoService flowFormInfoService;
    @Resource
    private FlowFormPersonService flowFormPersonService;
    @Resource
    private FlowFormRecordService flowFormRecordService;

    @Override
    public Result<?> saveDraft(FlowDraftDTO flowDraftDTO) {
        FlowInfo flowInfo = BeanUtil.copyProperties(flowDraftDTO, FlowInfo.class);
        flowInfo.insert(flowDraftDTO);
        return null;
    }
}
