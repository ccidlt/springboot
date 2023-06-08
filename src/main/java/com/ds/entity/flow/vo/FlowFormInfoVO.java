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
@ApiModel(value="FlowFormInfoVO对象", description="单子信息")
public class FlowFormInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4847082667745809142L;

    @ApiModelProperty(value = "单号")
    private String formNum;

    @ApiModelProperty(value = "状态：1草稿，2未完成，3已完成，4被拒绝，5关闭")
    private Integer formStatus;

}
