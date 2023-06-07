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
 * 审批执行表
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowFormExecute对象", description="审批执行表")
public class FlowFormExecute extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "flow_form_info表id")
    private Long flowFormId;

    @ApiModelProperty(value = "单号")
    private String formNum;

    @ApiModelProperty(value = "状态：1同意，2拒绝")
    private Integer executeStatus;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "工号")
    private String personCode;

    @ApiModelProperty(value = "所属部门编码")
    private String personDeptCode;

    @ApiModelProperty(value = "所属部门名称")
    private String personDeptName;


}
