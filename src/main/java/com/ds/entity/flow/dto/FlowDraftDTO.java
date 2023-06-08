package com.ds.entity.flow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
@ApiModel(value="FlowDraftDTO对象", description="暂存信息")
public class FlowDraftDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 3877614824880201274L;

    @ApiModelProperty(value = "请假原因")
    @NotBlank
    private String leaveReason;

    @ApiModelProperty(value = "审批人工号")
    @NotBlank
    private String approvalPersonCode;

    @ApiModelProperty(value = "审批人姓名")
    private String approvalPersonName;

    @ApiModelProperty(value = "审批人角色编码")
    private String approvalRoleCode;

}
