package com.ds.service.flow.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.ds.entity.flow.dto.FlowDraftDTO;
import com.ds.entity.flow.entity.FlowFormInfo;
import com.ds.entity.flow.entity.FlowFormPerson;
import com.ds.entity.flow.entity.FlowFormRecord;
import com.ds.entity.flow.entity.FlowInfo;
import com.ds.enums.flow.FormRecordStatusEnum;
import com.ds.enums.flow.FormStatusEnum;
import com.ds.service.flow.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDraft(FlowDraftDTO flowDraftDTO) {
        FlowInfo flowInfo = BeanUtil.copyProperties(flowDraftDTO, FlowInfo.class, "id");
        flowInfo.insert(flowDraftDTO);
        flowInfoService.save(flowInfo);
        //复制对象排除id
        FlowFormInfo flowFormInfo = BeanUtil.copyProperties(flowDraftDTO, FlowFormInfo.class, "id");
        flowFormInfo.setFlowId(flowInfo.getId());
        //利用redis生成唯一序列号
        String code = "GD"+new SimpleDateFormat("yyyyMMdd").format(new Date());
        Long number = stringRedisTemplate.opsForValue().increment("formnum"+":"+code);
        String formNum = code + StrUtil.fillBefore(number.toString(),'0',6);
        flowFormInfo.setFormNum(formNum);
        flowFormInfo.setFormStatus(FormStatusEnum.DRAFT.getValue());
        flowFormInfo.insert(flowDraftDTO);
        flowFormInfoService.save(flowFormInfo);
        FlowFormPerson flowFormPerson = BeanUtil.copyProperties(flowDraftDTO, FlowFormPerson.class, "id");
        flowFormPerson.setFlowFormId(flowFormInfo.getId());
        flowFormPerson.setFormNum(formNum);
        flowFormPerson.setPersonCode(flowDraftDTO.getApprovalPersonCode());
        flowFormPerson.setPersonName(flowDraftDTO.getApprovalPersonName());
        flowFormPerson.setRoleCode(flowDraftDTO.getApprovalRoleCode());
        flowFormPerson.insert(flowDraftDTO);
        flowFormPersonService.save(flowFormPerson);
        FlowFormRecord flowFormRecord = BeanUtil.copyProperties(flowDraftDTO, FlowFormRecord.class, "id");
        flowFormRecord.setFlowFormId(flowFormInfo.getId());
        flowFormRecord.setFormNum(formNum);
        flowFormRecord.setRecordStatus(FormRecordStatusEnum.DRAFT.getValue());
        flowFormRecord.insert(flowDraftDTO);
        flowFormRecordService.save(flowFormRecord);
        return true;
    }
}
