package com.ds.service.flow;

import com.ds.entity.Result;
import com.ds.entity.flow.dto.FlowDraftDTO;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 15:19
 */
public interface FlowService {
    Result<?> saveDraft(FlowDraftDTO flowDraftDTO);
}
