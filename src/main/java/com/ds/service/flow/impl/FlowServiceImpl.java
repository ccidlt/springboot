package com.ds.service.flow.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ds.entity.flow.dto.FlowDraftDTO;
import com.ds.entity.flow.dto.FlowFormPersonDraftDTO;
import com.ds.entity.flow.entity.FlowFormInfo;
import com.ds.entity.flow.entity.FlowFormPerson;
import com.ds.entity.flow.entity.FlowFormRecord;
import com.ds.entity.flow.entity.FlowInfo;
import com.ds.entity.flow.vo.FlowFormInfoVO;
import com.ds.entity.flow.vo.FlowFormNumVO;
import com.ds.entity.flow.vo.FlowInfoVO;
import com.ds.enums.flow.FormRecordStatusEnum;
import com.ds.enums.flow.FormStatusEnum;
import com.ds.service.flow.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public FlowFormNumVO saveDraft(FlowDraftDTO flowDraftDTO) {
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
        List<FlowFormPersonDraftDTO> flowFormPersonDraftDTOList = flowDraftDTO.getFlowFormPersonDraftDTOList();
        for (FlowFormPersonDraftDTO flowFormPersonDraftDTO : flowFormPersonDraftDTOList) {
            FlowFormPerson flowFormPerson = BeanUtil.copyProperties(flowFormPersonDraftDTO, FlowFormPerson.class, "id");
            flowFormPerson.setFlowFormId(flowFormInfo.getId());
            flowFormPerson.setFormNum(formNum);
            flowFormPerson.setPersonCode(flowFormPersonDraftDTO.getApprovalPersonCode());
            flowFormPerson.setPersonName(flowFormPersonDraftDTO.getApprovalPersonName());
            flowFormPerson.setRoleCode(flowFormPersonDraftDTO.getApprovalRoleCode());
            flowFormPerson.insert(flowDraftDTO);
            flowFormPersonService.save(flowFormPerson);
        }
        FlowFormRecord flowFormRecord = BeanUtil.copyProperties(flowDraftDTO, FlowFormRecord.class, "id");
        flowFormRecord.setFlowFormId(flowFormInfo.getId());
        flowFormRecord.setFormNum(formNum);
        flowFormRecord.setRecordStatus(FormRecordStatusEnum.DRAFT.getValue());
        flowFormRecord.insert(flowDraftDTO);
        flowFormRecordService.save(flowFormRecord);
        FlowFormNumVO flowFormNumVO = new FlowFormNumVO();
        flowFormNumVO.setFlowId(flowInfo.getId());
        flowFormNumVO.setFlowFormId(flowFormInfo.getId());
        flowFormNumVO.setFormNum(formNum);
        return flowFormNumVO;
    }

    @Override
    public List<FlowInfoVO> queryList() {
        List<FlowInfo> flowInfoList = flowInfoService.list(new QueryWrapper<FlowInfo>().lambda().orderByDesc(FlowInfo::getCreateTime));
        List<FlowInfoVO> flowInfoVOList = BeanUtil.copyToList(flowInfoList, FlowInfoVO.class);
        for (FlowInfoVO flowInfoVO : flowInfoVOList) {
            FlowFormInfo flowFormInfo = flowFormInfoService.getOne(new QueryWrapper<FlowFormInfo>().lambda().eq(FlowFormInfo::getFlowId, flowInfoVO.getId()).last("limit 1"));
            FlowFormInfoVO flowFormInfoVO = BeanUtil.copyProperties(flowFormInfo, FlowFormInfoVO.class);
            flowInfoVO.setFlowFormInfoVO(flowFormInfoVO);
        }
        return flowInfoVOList;
    }
}
