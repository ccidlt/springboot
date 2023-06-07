package com.ds.entity.flow.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 审批单信息表
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowFormInfo对象", description="审批单信息表")
public class FlowFormInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "flow_info表id")
    private Long flowId;

    @ApiModelProperty(value = "单号")
    private String formNum;

    @ApiModelProperty(value = "状态：1草稿，2未完成，3已完成，4被拒绝，5关闭")
    private Integer formStatus;
}
