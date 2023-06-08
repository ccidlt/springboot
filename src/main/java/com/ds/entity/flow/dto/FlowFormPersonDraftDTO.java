package com.ds.entity.flow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 人员暂存信息
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="FlowFormPersonDraftDTO对象", description="人员暂存信息")
public class FlowFormPersonDraftDTO implements Serializable {

    private static final long serialVersionUID = -3813169385710831918L;

    @ApiModelProperty(value = "审批人工号")
    @NotBlank
    private String approvalPersonCode;

    @ApiModelProperty(value = "审批人姓名")
    private String approvalPersonName;

    @ApiModelProperty(value = "审批人角色编码")
    private String approvalRoleCode;

}
