package com.ds.entity.flow.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 审批记录表
 * </p>
 *
 * @author lt
 * @since 2023-06-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FlowFormRecord对象", description="审批记录表")
public class FlowFormRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "flow_form_info表id")
    private Long flowFormId;

    @ApiModelProperty(value = "单号")
    private String formNum;

    @ApiModelProperty(value = "状态：1草稿，2创建，3同意，4拒绝")
    private Integer recordStatus;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "工号")
    private String personCode;

    @ApiModelProperty(value = "所属部门编码")
    private String personDeptCode;

    @ApiModelProperty(value = "所属部门名称")
    private String personDeptName;

    @ApiModelProperty(value = "0：正常，1：删除")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty(value = "0：启用，1：停用")
    private Integer isDisable;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "最后修改人id")
    private Long updateId;

    @ApiModelProperty(value = "最后修改人姓名")
    private String updateName;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    @Version
    private String dataVersion;


}
