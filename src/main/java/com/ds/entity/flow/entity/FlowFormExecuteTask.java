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
 * 审批执行任务表
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowFormExecuteTask对象", description="审批执行任务表")
public class FlowFormExecuteTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "flow_form_info表id")
    private Long flowFormId;

    @ApiModelProperty(value = "flow_form_execute表id")
    private Long flowFormExecuteId;

    @ApiModelProperty(value = "单号")
    private String formNum;

    @ApiModelProperty(value = "状态：0未执行，1执行中，2已执行")
    private Integer taskStatus;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务描述")
    private String taskDesc;

}
