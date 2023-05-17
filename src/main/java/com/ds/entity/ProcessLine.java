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
 * 流程线表
 * </p>
 *
 * @author lt
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProcessLine对象", description="流程线表")
public class ProcessLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "流程编号")
    private String processCode;

    @ApiModelProperty(value = "前一个节点编号")
    private String nodeCodePre;

    @ApiModelProperty(value = "后一个节点编号")
    private String nodeCodeNext;

    @ApiModelProperty(value = "条件")
    private String condition;

    @ApiModelProperty(value = "说明")
    private String description;


}
