package com.ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 流程业务表
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProcessBusiness对象", description="流程业务表")
public class ProcessBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报销人")
    private Long userId;

    @ApiModelProperty(value = "报销费用")
    private BigDecimal money;

    @TableField(exist = false)
    @ApiModelProperty(value = "状态")
    private String state;
    @TableField(exist = false)
    @ApiModelProperty(value = "流程编号")
    private String processCode = "BX";
    @TableField(exist = false)
    @ApiModelProperty(value = "上一个流程节点编号")
    private String preNodeCode;
    @TableField(exist = false)
    @ApiModelProperty(value = "当前流程线编号")
    private String lineCode;
    @TableField(exist = false)
    @ApiModelProperty(value = "下一个流程节点编号")
    private String nextNodeCode;

}
