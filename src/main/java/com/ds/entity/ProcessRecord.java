package com.ds.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 流程记录表
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProcessRecord对象", description="流程记录表")
public class ProcessRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "流程编号")
    private String processCode = "BX";

    @ApiModelProperty(value = "流程节点编号")
    private String nodeCode;

    @ApiModelProperty(value = "流程线编号")
    private String lineCode;

    @ApiModelProperty(value = "业务id")
    private Long businessId;

    @ApiModelProperty(value = "状态：1-发起；2-通过；3-驳回")
    private String approvalState;

    @ApiModelProperty(value = "操作结果")
    private String approvalResult;

    @ApiModelProperty(value = "操作人")
    private Long approvalUser;

    @ApiModelProperty(value = "操作时间")
    private Date approvalTime;


}
