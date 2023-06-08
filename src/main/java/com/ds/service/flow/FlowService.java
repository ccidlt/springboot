package com.ds.service.flow;

import com.ds.entity.flow.dto.FlowDraftDTO;
import com.ds.entity.flow.vo.FlowFormNumVO;
import com.ds.entity.flow.vo.FlowInfoVO;

import java.util.List;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 15:19
 */
public interface FlowService {

    FlowFormNumVO saveDraft(FlowDraftDTO flowDraftDTO);

    List<FlowInfoVO> queryList();
}
