package com.ds.entity.flow.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author lt
 * @Date 2023/6/7 10:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowInfoVO对象", description="主体信息")
public class FlowInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1154506746532909196L;

    @ApiModelProperty(value = "请假原因")
    private String leaveReason;

    @ApiModelProperty(value = "单子信息")
    private FlowFormInfoVO flowFormInfoVO;

}
