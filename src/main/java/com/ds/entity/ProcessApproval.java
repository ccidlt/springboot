package com.ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 流程审批人表
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProcessApproval对象", description="流程审批人表")
public class ProcessApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "流程表编号")
    private String processCode;

    @ApiModelProperty(value = "流程节点表编号")
    private String nodeCode;

    @ApiModelProperty(value = "分配人")
    private String userId;

    @ApiModelProperty(value = "分配组(角色)")
    private String userGroup;


}
